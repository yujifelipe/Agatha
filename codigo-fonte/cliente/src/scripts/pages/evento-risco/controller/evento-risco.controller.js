/**
 * Created by Basis Tecnologia on 03/08/2016.
 */
(function () {
    'use strict';
    angular.module('gestaoRiscosApp').controller('EventoRiscoController', ['$scope', '$state', '$stateParams', '$mdToast', '$mdDialog', '$timeout', '$q', 'EventoRiscoService', controller]);

    function controller($scope, $state, $stateParams, $mdToast, $mdDialog, $timeout, $q, service) {
        var scope = this;

        scope.causa = {};
        scope.consequencia = {};

        scope.processoId = $stateParams.id;

        scope.init = function () {
            scope.registro = {};
            scope.registro.riscoIntegridade = false;
            scope.registro.causas = [];
            scope.registro.consequencias = [];
            scope.getUsuarioLogado(function () {
                scope.findProcessoById();
                if ($stateParams.eventoRiscoId) {
                    scope.findBy($stateParams.eventoRiscoId);
                } else {
                    scope.findAllCategorias();
                }
            });

        };

        scope.getUsuarioLogado = function (callback) {
            service.getUsuarioLogado().then(function (retorno) {
                scope.usuario = retorno.data;
                if (callback) {
                    callback();
                }
            });
        };

        scope.findAllCategorias = function (callback) {
            service.findAllCategorias().then(function (retorno) {
                scope._categorias = retorno.data;
                if (callback) {
                    callback();
                }
            });
        };

        scope.findAllNaturezas = function (callback) {
            service.findAllNaturezas().then(function (retorno) {
                scope._naturezas = retorno.data;
                if (callback) {
                    callback();
                }
            });
        };

        scope.atualizarTodosEventos = function () {
            $mdDialog.cancel();
            service.atualizarEvento(scope.registro.evento).then(function () {
                scope.findBy($stateParams.eventoRiscoId);
            });
        };

        scope.substituirEvento = function () {
            $mdDialog.cancel();
            service.substituirEvento(scope.registro).then(function () {
                scope.findBy($stateParams.eventoRiscoId);
            });
        };

        scope.alterarCausa = function (item) {
            item.$update = true;
            scope.editarCausa = angular.copy(item);
        };

        scope.handleCausaChange = function () {
            if (scope.editarCausa.causa && scope.editarCausa.causa.descricao) {
                if (scope.verificaListagemCausa(scope.editarCausa.causa)) {
                    for (var i = 0; i < scope.registro.causas.length; i++) {
                        if (scope.registro.causas[i].$update) {
                            scope.registro.causas[i] = angular.copy(scope.editarCausa);
                            delete scope.registro.causas[i].$update;
                            scope.editarCausa = {};
                        }
                    }
                }
            }
        };

        scope.cancelarCausaUpdate = function () {
            for (var i = 0; i < scope.registro.causas.length; i++) {
                if (scope.registro.causas[i].$update) {
                    delete scope.registro.causas[i].$update;
                    scope.editarCausa = {};
                }
            }
        };

        scope.atualizarTodasCausas = function () {
            $mdDialog.cancel();
            service.atualizarCausa(scope.editarCausa.causa).then(function () {
                scope.findBy($stateParams.eventoRiscoId);
            });
        };

        scope.substituirCausa = function () {
            $mdDialog.cancel();
            service.substituirCausa(scope.editarCausa).then(function () {
                scope.findBy($stateParams.eventoRiscoId);
            });
        };

        scope.alterarConsequencia = function (item) {
            item.$update = true;
            scope.editarConsequencia = angular.copy(item);
        };

        scope.handleConsequenciaChange = function () {
            if (scope.editarConsequencia.consequencia && scope.editarConsequencia.consequencia.descricao) {
                if (scope.verificaListagemConsequencia(scope.editarConsequencia.consequencia)) {
                    for (var i = 0; i < scope.registro.consequencias.length; i++) {
                        if (scope.registro.consequencias[i].$update) {
                            scope.registro.consequencias[i] = angular.copy(scope.editarConsequencia);
                            delete scope.registro.consequencias[i].$update;
                            scope.editarConsequencia = {};
                        }
                    }
                }
            }
        };

        scope.cancelarConsequenciaUpdate = function () {
            for (var i = 0; i < scope.registro.consequencias.length; i++) {
                if (scope.registro.consequencias[i].$update) {
                    delete scope.registro.consequencias[i].$update;
                    scope.editarConsequencia = {};
                }
            }
        };

        scope.atualizarTodasConsequencias = function () {
            $mdDialog.cancel();
            service.atualizarConsequencia(scope.editarConsequencia.consequencia).then(function () {
                scope.findBy($stateParams.eventoRiscoId);
            });
        };

        scope.substituirConsequencia = function () {
            $mdDialog.cancel();
            service.substituirConsequencia(scope.editarConsequencia).then(function () {
                scope.findBy($stateParams.eventoRiscoId);
            });
        };

        scope.cancel = function () {
            scope.findBy($stateParams.eventoRiscoId);
            $mdDialog.cancel();
        };

        scope.getEventoBySearch = function () {
            if (scope.registro.evento && scope.registro.evento.descricao && scope.registro.evento.descricao.length >= 3) {
                if (scope.registro.evento.descricao.length >= 1000) {
                    scope.registro.evento.descricao = scope.registro.evento.descricao.substring(0, 1000);
                    //noinspection JSUnresolvedFunction
                    $mdToast.show(
                        $mdToast.simple()
                            .textContent('Esta descrição pode ter no máximo 1000 caracteres!')
                            .hideDelay(3000)
                    );
                }
                service.getEventoBySearch(scope.registro.evento.descricao, scope.usuario).then(function (objectReturn) {
                    // $('#evento-risco').find('input').blur();
                    // $timeout(function () {
                    //     $('#evento-risco').find('input').focus();
                    // });
                    scope._eventos = objectReturn.data;
                    return objectReturn.data;
                });
            } else {
                scope._eventos = [];
                var deferred = $q.defer();
                deferred.resolve([]);
                return deferred.promise;
            }
        };

        scope.findProcessoById = function () {
            scope.processo = {};
            service.findProcessoById(scope.processoId).then(function (objectResponse) {
                scope.processo = objectResponse.data;

            });
        };

        scope.getCausasBySearch = function () {
            if (scope.causa.descricao && scope.causa.descricao.length >= 3) {
                if (scope.causa.descricao.length >= 1000) {
                    scope.causa.descricao = scope.causa.descricao.substring(0, 1000);
                    //noinspection JSUnresolvedFunction
                    $mdToast.show(
                        $mdToast.simple()
                            .textContent('Esta descrição pode ter no máximo 1000 caracteres!')
                            .hideDelay(3000)
                    );
                }
                service.getCausasBySearch(scope.causa.descricao, scope.usuario).then(function (objectReturn) {
                    $('#causa-risco').find('input').blur();
                    $timeout(function () {
                        $('#causa-risco').find('input').focus();
                    });
                    scope._causas = objectReturn.data;
                    return objectReturn.data;
                });
            } else {
                scope._causas = [];
                var deferred = $q.defer();
                deferred.resolve([]);
                return deferred.promise;
            }
        };

        scope.getEditarCausasBySearch = function () {
            if (scope.editarCausa.causa && scope.editarCausa.causa.descricao && scope.editarCausa.causa.descricao.length >= 3) {
                if (scope.editarCausa.causa.descricao.length >= 1000) {
                    scope.editarCausa.causa.descricao = scope.editarCausa.causa.descricao.substring(0, 1000);
                    //noinspection JSUnresolvedFunction
                    $mdToast.show(
                        $mdToast.simple()
                            .textContent('Esta descrição pode ter no máximo 1000 caracteres!')
                            .hideDelay(3000)
                    );
                }
                service.getCausasBySearch(scope.editarCausa.causa.descricao, scope.usuario).then(function (objectReturn) {
                    $('#causa-risco-editar').find('input').blur();
                    $timeout(function () {
                        $('#causa-risco-editar').find('input').focus();
                    });
                    scope._causas = objectReturn.data;
                    return objectReturn.data;
                });
            } else {
                scope._causas = [];
                var deferred = $q.defer();
                deferred.resolve([]);
                return deferred.promise;
            }
        };

        scope.getConsequenciasBySearch = function () {
            if (scope.consequencia.descricao && scope.consequencia.descricao.length >= 3) {
                if (scope.consequencia.descricao.length >= 1000) {
                    scope.consequencia.descricao = scope.consequencia.descricao.substring(0, 1000);
                    //noinspection JSUnresolvedFunction
                    $mdToast.show(
                        $mdToast.simple()
                            .textContent('Esta descrição pode ter no máximo 1000 caracteres!')
                            .hideDelay(3000)
                    );
                }
                service.getConsequenciasBySearch(scope.consequencia.descricao, scope.usuario).then(function (objectReturn) {
                    $('#consequencia-risco').find('input').blur();
                    $timeout(function () {
                        $('#consequencia-risco').find('input').focus();
                    });
                    scope._consequencias = objectReturn.data;
                    return objectReturn.data;
                });
            } else {
                scope._consequencias = [];
                var deferred = $q.defer();
                deferred.resolve([]);
                return deferred.promise;
            }
        };

        scope.getEditarConsequenciasBySearch = function () {
            if (scope.editarConsequencia.consequencia && scope.editarConsequencia.consequencia.descricao && scope.editarConsequencia.consequencia.descricao.length >= 3) {
                if (scope.editarConsequencia.consequencia.descricao.length >= 1000) {
                    scope.editarConsequencia.consequencia.descricao = scope.editarConsequencia.consequencia.descricao.substring(0, 1000);
                    //noinspection JSUnresolvedFunction
                    $mdToast.show(
                        $mdToast.simple()
                            .textContent('Esta descrição pode ter no máximo 1000 caracteres!')
                            .hideDelay(3000)
                    );
                }
                service.getConsequenciasBySearch(scope.editarConsequencia.consequencia.descricao, scope.usuario).then(function (objectReturn) {
                    $('#consequencia-risco-editar').find('input').blur();
                    $timeout(function () {
                        $('#consequencia-risco-editar').find('input').focus();
                    });
                    scope._consequencias = objectReturn.data;
                    return objectReturn.data;
                });
            } else {
                scope._consequencias = [];
                var deferred = $q.defer();
                deferred.resolve([]);
                return deferred.promise;
            }
        };

        scope.verificaListagemCausa = function (causa) {
            var hasInList = false;
            for (var i in scope.registro.causas) {
                if (scope.registro.causas[i].causa.descricao == causa.descricao && (!causa.id || causa.id != scope.registro.causas[i].causa.id)) {
                    hasInList = true;
                    break;
                }
            }
            if (hasInList) {
                //noinspection JSUnresolvedFunction
                $mdToast.show(
                    $mdToast.simple()
                        .textContent('Causa já cadastrada!')
                        .hideDelay(3000)
                );
            }
            return !hasInList;
        };

        scope.adicionarCausa = function () {
            if (scope.causa && scope.causa.descricao) {
                if (scope.verificaListagemCausa(scope.causa)) {
                    var causaDTO = {};
                    causaDTO.causa = angular.copy(scope.causa);
                    scope.registro.causas.push(causaDTO);

                    scope.causa = {};
                }
            }
        };

        scope.excluirCausa = function (item) {

            var confirm = $mdDialog.confirm()
                .title('Atenção!')
                .textContent('Deseja excluir o registro?')
                .ok('OK')
                .cancel('Cancelar');

            $mdDialog.show(confirm).then(function () {
                for (var i = 0; i < scope.registro.causas.length; i++) {
                    if (angular.equals(scope.registro.causas[i], item)) {
                        scope.registro.causas.splice(i, 1);
                    }
                }
            });
        };

        scope.verificaListagemConsequencia = function (consequencia) {
            var hasInList = false;
            for (var i in scope.registro.consequencias) {
                if (scope.registro.consequencias[i].consequencia.descricao == consequencia.descricao && (!consequencia.id || consequencia.id != scope.registro.consequencias[i].consequencia.id)) {
                    hasInList = true;
                    break;
                }
            }
            if (hasInList) {
                //noinspection JSUnresolvedFunction
                $mdToast.show(
                    $mdToast.simple()
                        .textContent('Consequência já cadastrada!')
                        .hideDelay(3000)
                );
            }
            return !hasInList;
        };

        scope.adicionarConsequencia = function () {
            if (scope.consequencia && scope.consequencia.descricao) {
                if (scope.verificaListagemConsequencia(scope.consequencia)) {
                    var consequenciaDTO = {};
                    consequenciaDTO.consequencia = angular.copy(scope.consequencia);
                    scope.registro.consequencias.push(consequenciaDTO);

                    scope.consequencia = {};
                }
            }
        };

        scope.excluirConsequencia = function (item) {

            var confirm = $mdDialog.confirm()
                .title('Atenção!')
                .textContent('Deseja excluir o registro?')
                .ok('OK')
                .cancel('Cancelar');

            $mdDialog.show(confirm).then(function () {
                for (var i = 0; i < scope.registro.consequencias.length; i++) {
                    if (angular.equals(scope.registro.consequencias[i], item)) {
                        scope.registro.consequencias.splice(i, 1);
                    }
                }
            });
        };

        scope.findBy = function (registroId) {
            scope.registro = {};
            scope.editarCausa = {};
            scope.editarConsequencia = {};
            service.findBy(registroId).then(function (objectResponse) {
                scope.registro = objectResponse.data;

                scope.findAllCategorias(function () {
                    for (var i = 0; i < scope._categorias.length; i++) {
                        if (scope._categorias[i].id == scope.registro.categoria.id) {
                            scope.registro.categoria = scope._categorias[i];
                        }
                    }
                });
            }, function (error) {
                if (error.status === 404) {
                    //noinspection JSUnresolvedFunction
                    $mdToast.show(
                        $mdToast.simple()
                            .textContent('Evento de risco não encontrado')
                            .hideDelay(3000)
                    );

                    window.history.back();
                }
            });
        };

        scope.persistir = function (registro) {
            if ($scope.formRegistro.$valid) {
                if (scope.registro.causas && scope.registro.causas.length) {
                    if (scope.registro.consequencias && scope.registro.consequencias.length) {
                        registro.cpf = scope.usuario.cpf;
                        registro.identificacao = {
                            processo: {
                                id: $stateParams.id
                            }
                        };
                        if (registro.id) {
                            service.update(registro, scope.usuario).then(function () {
                                $state.go('processo.identificacao.alterar', {id: scope.processoId});
                            });
                        } else {
                            service.create(registro, scope.usuario).then(function () {
                                $state.go('processo.identificacao.alterar', {id: scope.processoId});
                            });
                        }
                    } else {
                        //noinspection JSUnresolvedFunction
                        $mdToast.show(
                            $mdToast.simple()
                                .textContent('Informe pelo menos uma consequência')
                                .hideDelay(3000)
                        );
                    }
                } else {
                    //noinspection JSUnresolvedFunction
                    $mdToast.show(
                        $mdToast.simple()
                            .textContent('Informe pelo menos uma causa')
                            .hideDelay(3000)
                    );
                }
            } else {
                //noinspection JSUnresolvedFunction
                $mdToast.show(
                    $mdToast.simple()
                        .textContent('Campo(s) obrigatório(s) não preenchido(s)')
                        .hideDelay(3000)
                );
            }
        };

        scope.salvarTaxonomiaEvento = function () {
            if (scope.registro.evento && scope.registro.evento.descricao) {
                service.salvarTaxonomiaEvento(scope.registro.evento, scope.usuario).then(function (objectReturn) {
                    scope.registro.evento = objectReturn.data.evento;
                });
            }
        };

        scope.salvarTaxonomiaCausa = function (item) {
            if (item.causa && item.causa.descricao) {
                service.salvarTaxonomiaCausa(item.causa, scope.usuario).then(function (objectReturn) {
                    item.causa = objectReturn.data.causa;
                });
            }
        };

        scope.salvarTaxonomiaConsequencia = function (item) {
            if (item.consequencia && item.consequencia.descricao) {
                service.salvarTaxonomiaConsequencia(item.consequencia, scope.usuario).then(function (objectReturn) {
                    item.consequencia = objectReturn.data.consequencia;
                });
            }
        };

        scope.incluirCausaAndFocus = function () {
            scope.incluirCausa = true;
            $timeout(function () {
                $('#causa-risco').focus();
            });
        };

        scope.adicionarCausaInline = function () {
            if (scope.causa && scope.causa.descricao) {
                if (scope.verificaListagemCausa(scope.causa)) {
                    var causaDTO = {};
                    causaDTO.causa = angular.copy(scope.causa);
                    scope.registro.causas.push(causaDTO);

                    scope.causa = {};

                    scope.incluirCausa = false;
                }
            } else {
                //noinspection JSUnresolvedFunction
                $mdToast.show(
                    $mdToast.simple()
                        .textContent('Preencha o campo corretamente')
                        .hideDelay(3000)
                );
            }
        };

        scope.incluirConsequenciaAndFocus = function () {
            scope.incluirConsequencia = true;
            $timeout(function () {
                $('#consequencia-risco').focus();
            });
        };

        scope.adicionarConsequenciaInline = function () {
            if (scope.consequencia && scope.consequencia.descricao) {
                if (scope.verificaListagemConsequencia(scope.consequencia)) {
                    var consequenciaDTO = {};
                    consequenciaDTO.consequencia = angular.copy(scope.consequencia);
                    scope.registro.consequencias.push(consequenciaDTO);

                    scope.consequencia = {};

                    scope.incluirConsequencia = false;
                }
            } else {
                //noinspection JSUnresolvedFunction
                $mdToast.show(
                    $mdToast.simple()
                        .textContent('Preencha o campo corretamente')
                        .hideDelay(3000)
                );
            }
        };

        scope.init();
    }
})();
