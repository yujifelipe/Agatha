/**
 * Created by Basis Tecnologia on 25/04/2017.
 */
(function () {
    'use strict';
    angular.module('gestaoRiscosApp').controller('PlanoControleController', ['$scope', '$rootScope', '$state', '$stateParams', '$mdToast', '$mdDialog', '$q', 'PlanoControleService', '$timeout', controller]);

    function controller($scope, $rootScope, $state, $stateParams, $mdToast, $mdDialog, $q, service, $timeout) {
        var scope = this;

        scope.processoId = $state.params.id;

        scope.apenasLeitura = false;

        scope.init = function () {
            scope._eventoRisco = {};
            scope._eventoRisco.controles = [];
            scope.getUsuarioLogado(function () {

                scope.novoRegistro();
                if ($state.current.name.indexOf('consultar') != -1 || $state.current.name.indexOf('alterar') != -1) {
                    scope.getPermissao();
                } else {
                    scope.registro.dtInicio = '';
                    scope.registro.dtConclusao = '';
                    scope.findByEventoRisco($stateParams.eventoRiscoId);
                    scope.findAllTiposControle();
                    scope.findAllObjetivosControle();
                }
            });
        };

        scope.novoRegistro = function () {
            scope.registro = {};
            scope.registro.dtInicio = '';
            scope.registro.dtConclusao = '';
        };

        scope.filtrar = function () {
            scope.filtros.page = scope.registroInicial - 1;
            scope.filtros.size = scope.tamanhoLimite;

            service.findByFilter(scope.filtros, scope.processoId).then(function (retorno) {
                scope._eventosRisco = retorno.data.content || [];
                scope.totalItems = retorno.data.totalElements;
            });
        };

        scope.limparFiltros = function () {
            scope.filtros = {};

            scope.filtrar();
        };

        scope.getUsuarioLogado = function (callback) {
            service.getUsuarioLogado().then(function (retorno) {
                scope.usuario = retorno.data;
                if (callback) {
                    callback();
                }
            });
        };

        scope.findByEventoRisco = function (registroId) {
            scope._eventoRisco = {};
            service.findByEventoRisco(registroId).then(function (objectResponse) {
                scope._eventoRisco = objectResponse.data;
            });
        };

        scope.getPermissao = function () {
            service.getPermissao(scope.usuario.cpf).then(function (objectReturn) {
                scope.permissao = objectReturn.data;

                scope.apenasLeitura = $state.current.name.indexOf('consultar') != -1;
                scope.apenasLeitura = scope.permissao.criar == false ? true : scope.apenasLeitura;

                scope.registroInicial = 1;
                scope.tamanhoLimite = 20;
                scope.totalItems = 0;
                scope.limparFiltros();

            });
        };

        scope.handleControleChange = function () {
            $scope.formRegistro.$setSubmitted();
            if ($scope.formRegistro.$valid && scope.registro.dtInicio && scope.registro.dtConclusao) {
                scope.registro.eventoRisco = {};
                scope.registro.eventoRisco.id = $stateParams.eventoRiscoId;
                scope.registro.cpf = scope.usuario.cpf;
                service.update(scope.registro).then(function (objectReturn) {
                    for (var i = 0; i < scope._eventoRisco.controles.length; i++) {
                        if (scope._eventoRisco.controles[i].$update) {
                            scope._eventoRisco.controles[i] = angular.copy(objectReturn.data);
                            delete scope._eventoRisco.controles[i].$update;
                            scope.registro = {};
                            scope.registro.dtInicio = '';
                            scope.registro.dtConclusao = '';
                        }
                    }
                    scope.registro = {};
                    scope.registro.dtInicio = '';
                    scope.registro.dtConclusao = '';
                    $scope.formRegistro.$setPristine();
                    $scope.formRegistro.$setUntouched();
                });
            } else {
                //noinspection JSUnresolvedFunction
                $mdToast.show(
                    $mdToast.simple()
                        .textContent('Campo(s) obrigatório(s) não preenchido(s)')
                        .hideDelay(3000)
                );
            }
        };

        scope.cancelarControleUpdate = function () {
            for (var i = 0; i < scope._eventoRisco.controles.length; i++) {
                if (scope._eventoRisco.controles[i].$update) {
                    delete scope._eventoRisco.controles[i].$update;
                    scope.registro = {};
                    scope.registro.dtInicio = '';
                    scope.registro.dtConclusao = '';
                }
            }
        };

        scope.adicionarControleEvento = function () {
            $scope.formRegistro.$setSubmitted();
            if ($scope.formRegistro.$valid && scope.registro.dtInicio && scope.registro.dtConclusao) {
                scope.registro.eventoRisco = {};
                scope.registro.eventoRisco.id = $stateParams.eventoRiscoId;
                scope.registro.cpf = scope.usuario.cpf;
                service.create(scope.registro).then(function (objectReturn) {
                    scope._eventoRisco.controles.push(objectReturn.data);
                    scope.registro = {};
                    scope.registro.dtInicio = '';
                    scope.registro.dtConclusao = '';
                    $scope.formRegistro.$setPristine();
                    $scope.formRegistro.$setUntouched();
                });
            } else {
                //noinspection JSUnresolvedFunction
                $mdToast.show(
                    $mdToast.simple()
                        .textContent('Campo(s) obrigatório(s) não preenchido(s)')
                        .hideDelay(3000)
                );
            }
        };

        scope.excluirControleEvento = function (item) {

            var confirm = $mdDialog.confirm()
                .title('Atenção!')
                .textContent('Deseja excluir o registro?')
                .ok('OK')
                .cancel('Cancelar');

            $mdDialog.show(confirm).then(function () {
                service.excluir(item.id).then(function () {
                    for (var i = 0; i < scope._eventoRisco.controles.length; i++) {
                        if (angular.equals(scope._eventoRisco.controles[i], item)) {
                            scope._eventoRisco.controles.splice(i, 1);
                        }
                    }
                });
            });
        };

        scope.findAllTiposControle = function (callback) {
            service.findAllTiposControle().then(function (retorno) {
                scope._tiposControle = retorno.data;
                if (callback) {
                    callback();
                }
            });
        };

        scope.findAllObjetivosControle = function (callback) {
            service.findAllObjetivosControle().then(function (retorno) {
                scope._objetivosControle = retorno.data;
                if (callback) {
                    callback();
                }
            });
        };

        scope.alterarControleEvento = function (item) {
            scope.novoRegistro();

            item.$update = true;
            var registro = angular.copy(item);

            if (registro.dtInicio) {
                registro.dtInicio = new Date(registro.dtInicio);
            } else {
                registro.dtInicio = '';
            }
            if (registro.dtConclusao) {
                registro.dtConclusao = new Date(registro.dtConclusao);
            } else {
                registro.dtConclusao = '';
            }

            for (var i = 0; i < scope._tiposControle.length; i++) {
                if (scope._tiposControle[i].id == registro.tipoControle.id) {

                    registro.tipoControle = scope._tiposControle[i];
                }
            }
            for (var i = 0; i < scope._objetivosControle.length; i++) {
                if (scope._objetivosControle[i].id == registro.objetivo.id) {

                    registro.objetivo = scope._objetivosControle[i];
                }
            }

            $timeout(function () {
                scope.registro = registro;
            });
        };

        scope.getControlesBySearch = function (descricao) {
            if (descricao && descricao.length >= 3) {
                service.getControlesBySearch(descricao, scope.usuario).then(function (objectReturn) {
                    scope._controles = objectReturn.data;
                    return objectReturn.data;
                });
            } else {
                scope._controles = [];
                var deferred = $q.defer();
                deferred.resolve([]);
                return deferred.promise;
            }
        };

        scope.voltar = function () {
            $state.go('processo.planoControle.alterar', {id: scope.processoId}, {reload: true});
        };

        scope.solicitarValidacao = function () {
            service.solicitarValidacao(scope.processoId).then(function () {
                //noinspection JSUnresolvedFunction
                $mdToast.show(
                    $mdToast.simple()
                        .textContent('Solicitação enviada com sucesso!')
                        .hideDelay(3000)
                );
            });
        };

        scope.init();
    }
})();
