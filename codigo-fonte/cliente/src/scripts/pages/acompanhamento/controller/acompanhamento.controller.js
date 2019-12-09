/**
 * Created by Basis Tecnologia on 25/04/2017.
 */
(function () {
    'use strict';
    angular.module('gestaoRiscosApp').controller('AcompanhamentoController', ['$scope', '$state', '$stateParams', '$mdToast', '$mdDialog', 'AcompanhamentoService', 'AnaliseAmbienteService', '$timeout', controller]);

    function controller($scope, $state, $stateParams, $mdToast, $mdDialog, service, AnaliseAmbienteService, $timeout) {
        var scope = this;

        var delayPromise;

        scope.registro = {};
        scope.planoControle = {};

        scope.processoId = $state.params.id;
        scope.eventoRiscoId = $stateParams.eventoRiscoId;

        scope.incluirRegistro = false;
        scope.exibirAviso = false;

        scope.init = function () {

            scope.getProcessoById(scope.processoId);
            scope.findByEventoRisco(scope.eventoRiscoId, function () {
                scope.registro = {};
                scope.registro.planoControleId = scope.planoControle.id;

                scope.filtrar();
            });

            scope._status = [
                'A iniciar',
                'Iniciada',
                'Concluída',
                'Cancelada'
            ];

            scope._implementacoes = [
                'Sim',
                'Não',
                'Parcialmente'
            ];
        };

        scope.filtrar = function () {
            service.findAll(scope.planoControle.id).then(function (retorno) {
                scope._registros = retorno.data;

                scope.verificaAviso();
            });
        };

        scope.excluirRegistro = function (registro) {
            scope.registro = {};

            var confirm = $mdDialog.confirm()
                .title('Atenção!')
                .textContent('Deseja excluir o registro?')
                .ok('OK')
                .cancel('Cancelar');

            $mdDialog.show(confirm).then(function () {
                service.excluir(registro.id).then(function () {
                    scope.filtrar();
                });
            });
        };

        scope.novoRegistro = function (incluirRegistro) {
            $scope.formRegistro.$setPristine();
            $scope.formRegistro.$setUntouched();

            scope.registro = {};
            scope.registro.planoControleId = scope.planoControle.id;

            scope.incluirRegistro = incluirRegistro;
        };

        scope.persistir = function (registro) {
            if ($scope.formRegistro.$valid) {
                service.create(registro).then(function () {
                    scope.novoRegistro(false);
                    scope.filtrar();
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

        scope.downloadAnexos = function (anexos) {
            scope.download(0, anexos);
        };

        scope.download = function (index, anexos) {
            if (anexos.length > index) {
                $timeout(function () {
                    window.open('/gestaoriscos/api/processos/download/' + anexos[index].id, '_blank');
                    scope.download(index + 1, anexos);
                }, 1000);
            }
        };

        scope.getProcessoById = function (registroId) {
            AnaliseAmbienteService.findBy(registroId).then(function (objectResponse) {
                scope._processo = objectResponse.data;
            });
        };

        scope.findByEventoRisco = function (registroId, callback) {
            scope._eventoRisco = {};
            service.findByEventoRisco(registroId).then(function (objectResponse) {
                scope._eventoRisco = objectResponse.data;

                if (scope._eventoRisco.controles.length) {
                    scope.planoControle = scope._eventoRisco.controles[0];
                    scope.planoControle.index = 0;
                }

                callback();
            });
        };

        $scope.$watch('scope.files.length', function () {
            if (delayPromise) {
                $timeout.cancel(delayPromise);
            }

            delayPromise = $timeout(function () {
                if (scope.files && scope.files.length) {
                    validaExtencoes();
                    if (scope.registro.anexos && scope.registro.anexos.length) {
                        validaTamanho();
                    }

                    AnaliseAmbienteService.uploadFile(scope.files).then(function (objectReturn) {
                        if (!scope.registro.anexos) {
                            scope.registro.anexos = [];
                        }
                        angular.forEach(objectReturn.data, function (obj) {
                            scope.registro.anexos.push(obj);
                        });
                        scope.uploadApi.removeAll();
                    });
                }
            }, 1000);

            function validaExtencoes() {
                var removedFile = false;
                for (var i = 0; i < scope.files.length; i++) {
                    if (scope.files[i].lfFileType != 'image/png' &&
                        scope.files[i].lfFileType != 'image/jpeg' &&
                        scope.files[i].lfFileType != 'image/bpmn' &&
                        scope.files[i].lfFileType != 'application/pdf' &&
                        scope.files[i].lfFileType != 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' &&
                        scope.files[i].lfFileType != 'application/vnd.ms-excel' &&
                        scope.files[i].lfFileType != 'application/msword' &&
                        scope.files[i].lfFileType != 'application/vnd.openxmlformats-officedocument.wordprocessingml.document' &&
                        scope.files[i].lfFileType != 'application/x-rar-compressed' &&
                        scope.files[i].lfFileType != 'application/zip' &&
                        scope.files[i].lfFileType != 'application/x-zip-compressed' &&
                        scope.files[i].lfFileType != 'application/octet-stream'
                    ) {
                        scope.files.splice(i, 1);
                        removedFile = true;
                    }
                }

                if (removedFile) {
                    //noinspection JSUnresolvedFunction
                    $mdToast.show(
                        $mdToast.simple()
                            .textContent('Um o mais arquivos foram removidos pois não são suportados pelo sistema')
                            .hideDelay(3000)
                    );
                }
            }

            function validaTamanho() {
                var tamanhoAtual = 0;
                for (var i = 0; i < scope.registro.anexos.length; i++) {
                    tamanhoAtual += scope.registro.anexos[i].tamanho;
                }

                var removedFile = false;
                for (var i = 0; i < scope.files.length; i++) {
                    //var tamanhoRelativo = (tamanhoAtual / 1024 / 1024);
                    var tamanhoFuturo = ((tamanhoAtual + scope.files[i].lfFile.size) / 1024 / 1024);

                    if (tamanhoFuturo > 25) {
                        scope.files.splice(i, 1);
                        removedFile = true;
                    } else {
                        tamanhoAtual += scope.files[i].lfFile.size;
                    }
                }

                if (removedFile) {
                    //noinspection JSUnresolvedFunction
                    $mdToast.show(
                        $mdToast.simple()
                            .textContent('Um o mais arquivos foram removidos pois iriam extrapolar o limite de 25MB por processo')
                            .hideDelay(3000)
                    );
                }
            }
        });

        scope.excluirAnexo = function (index) {
            var confirm = $mdDialog.confirm()
                .title('Atenção!')
                .textContent('Deseja excluir o anexo?')
                .ok('OK')
                .cancel('Cancelar');

            $mdDialog.show(confirm).then(function () {
                scope.registro.anexos.splice(index, 1);
            });
        };

        scope.avancarPlano = function () {
            var index = angular.copy(scope.planoControle.index);
            index++;
            if (index < scope._eventoRisco.controles.length) {

                scope.planoControle = scope._eventoRisco.controles[index];
                scope.planoControle.index = index;

                scope.filtrar();
            }
        };

        scope.voltarPlano = function () {
            var index = angular.copy(scope.planoControle.index);
            if (index > 0) {
                index--;

                scope.planoControle = scope._eventoRisco.controles[index];
                scope.planoControle.index = index;

                scope.filtrar();
            }
        };

        scope.verificaAviso = function () {
            var hoje = new Date().setHours(0, 0, 0, 0);

            var dtConclusao = new Date(scope.planoControle.dtConclusao).setHours(0, 0, 0, 0);

            scope.exibirAviso = !!(hoje > dtConclusao);
        };

        scope.init();
    }
})();
