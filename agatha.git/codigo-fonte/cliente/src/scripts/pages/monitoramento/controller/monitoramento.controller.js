/**
 * Created by Basis Tecnologia on 19/09/2018.
 */
(function () {
    'use strict';
    angular.module('gestaoRiscosApp').controller('MonitoramentoController', ['$scope', '$mdToast', '$mdDialog', '$q', 'MonitoramentoService', 'usuarioService', controller]);

    function controller($scope, $mdToast, $mdDialog, $q, service, usuarioService) {
        var scope = this;

        scope.idOrgaoPai = 2981;
        scope.idCategoriaUnidadeAdministrativa = 1;
        scope.idCategoriaOrgao = 4;
        scope.listaIdCategorias = [scope.idCategoriaUnidadeAdministrativa, scope.idCategoriaUnidadeAdministrativa];

        scope._macroprocessos = [];
        scope._categorias = [];
        scope._fatores = [];
        scope._niveis = [];
        scope.colors = [
            '#00b052',
            '#ffff00',
            '#e26f01',
            '#fe0100'
        ];

        scope.options = {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true,
                        callback: function (value) {
                            if (value % 1 === 0) {
                                return value;
                            }
                        }
                    }
                }]
            }
        };

        scope.init = function () {
            scope.verificaPermissao(function () {

                scope.registroInicial = 1;
                scope.tamanhoLimite = 20;
                scope.totalItems = 0;
                scope.limparFiltros();

                scope.iniciarRegistro();
                scope.getAllSecretarias();
                scope.getAllMacroprocessos();
                scope.getAllCategorias();
                scope.getIntegtidades();
                scope.getNiveisRisco();
            });
        };

        scope.iniciarRegistro = function () {
            scope.registro = {
                secretarias: [],
                macroprocessos: [],
                categorias: [],
                integridades: [],
                niveisRisco: [],
                operadorMacropocesso: false,
                operadorCategoria: false,
                operadorIntegridade: false,
                operadorNivelRisco: false,
                operadorInerente: false,
                operadorResidual: false,
                operadorConclusao: false,
                riscosResiduais: [],
                riscosInerentes: [],
                dtInicio: '',
                dtFim: ''

            };

            if (!scope.usuarioLogado.isNucleo) {
                scope.registro.secretarias.push(angular.copy(scope.usuarioLogado.permissao.orgao));
            }

            scope.gerarGrafico();
        };

        scope.filtrar = function () {
            if ($scope.formRegistro) {
                $scope.formRegistro.$setPristine();
                $scope.formRegistro.$setUntouched();
            }

            scope.filtros.page = scope.registroInicial - 1;
            scope.filtros.size = scope.tamanhoLimite;

            service.findByFilter(scope.filtros, scope.registro).then(function (retorno) {
                scope._registros = retorno.data.content || [];
                scope.totalItems = retorno.data.totalElements;

                if (!scope._registros.length) {
                    //noinspection JSUnresolvedFunction
                    $mdToast.show(
                        $mdToast.simple()
                            .textContent('Dados não encontrados')
                            .hideDelay(3000)
                    );
                }
            });
        };

        scope.limparFiltros = function () {
            if ($scope.formRegistro) {
                $scope.formRegistro.$setPristine();
                $scope.formRegistro.$setUntouched();
            }

            scope.filtros = {};
            scope.iniciarRegistro();

            scope.filtrar();
        };

        scope.alterarStatus = function (registro) {
            registro.status = !registro.status;
            service.update(registro).then(function () {
                scope.filtrar();
            });
        };

        scope.excluirRegistro = function (registro) {
            scope.iniciarRegistro();

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

        scope.findBy = function (registroId) {
            $scope.formRegistro.$setPristine();
            $scope.formRegistro.$setUntouched();

            scope.iniciarRegistro();
            service.findBy(registroId).then(function (objectResponse) {
                var registro = angular.copy(objectResponse.data);
                scope.registro = angular.copy(objectResponse.data);
                scope.registro.secretarias = [];
                scope.registro.macroprocessos = [];
                scope.registro.categorias = [];

                if (scope.registro.dtCadastro) {
                    scope.registro.dtCadastro = new Date(scope.registro.dtCadastro);
                }

                if (scope.registro.dtInicio) {
                    scope.registro.dtInicio = new Date(scope.registro.dtInicio);
                } else {
                    scope.registro.dtInicio = '';
                }

                if (scope.registro.dtFim) {
                    scope.registro.dtFim = new Date(scope.registro.dtFim);
                } else {
                    scope.registro.dtFim = '';
                }

                for (var j = 0; j < registro.secretarias.length; j++) {

                    for (var i = 0; i < scope.orgaos.length; i++) {

                        if (registro.secretarias[j].id === scope.orgaos[i].id) {
                            scope.registro.secretarias.push(scope.orgaos[i]);
                            break;
                        }

                    }

                }

                for (var j = 0; j < registro.macroprocessos.length; j++) {

                    for (var i = 0; i < scope._macroprocessos.length; i++) {

                        if (registro.macroprocessos[j].id === scope._macroprocessos[i].id) {
                            scope.registro.macroprocessos.push(scope._macroprocessos[i]);
                            break;
                        }

                    }

                }

                for (var j = 0; j < registro.categorias.length; j++) {

                    for (var i = 0; i < scope._categorias.length; i++) {

                        if (registro.categorias[j].id === scope._categorias[i].id) {
                            scope.registro.categorias.push(scope._categorias[i]);
                            break;
                        }

                    }

                }

                scope.gerarGrafico();
            });
        };

        scope.persistir = function (ev) {
            if ($scope.formRegistro.$valid) {

                if (!scope.registro.id) {
                    scope.registro.dtCadastro = new Date();
                }

                $mdDialog.show({
                    controller: function () {
                        return scope;
                    },
                    controllerAs: 'ctrl',
                    templateUrl: 'scripts/pages/monitoramento/view/monitoramento-cadastro.tmpl.html',
                    parent: angular.element(document.body),
                    targetEvent: ev,
                    clickOutsideToClose: true,
                    fullscreen: true
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

        scope.salvar = function (registro) {
            if ($scope.formRegistro.$valid) {

                scope.cancel();
                if (registro.id) {
                    service.update(registro).then(function () {
                        scope.iniciarRegistro();
                        scope.filtrar();
                    });
                } else {
                    service.create(registro).then(function () {
                        scope.iniciarRegistro();
                        scope.filtrar();
                    });
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

        scope.limparCadastro = function () {
            scope.iniciarRegistro();
        };

        scope.cancel = function () {
            $mdDialog.cancel();
        };

        scope.verificaPermissao = function (callback) {
            usuarioService.getUsuarioLogado().then(function (objectReturn) {
                scope.usuarioLogado = objectReturn.data;
                usuarioService.getPermissao(scope.usuarioLogado.cpf).then(function (objectReturn) {
                    scope.usuarioLogado.permissao = objectReturn.data;

                    for (var i = 0; i < scope.usuarioLogado.permissoes.length; i++) {
                        var permissao = scope.usuarioLogado.permissoes[i];

                        if (!permissao.excluido) {
                            if (permissao.perfil.id === usuarioService.getPerfilNucleo()) {
                                scope.usuarioLogado.isNucleo = true;
                            } else if (permissao.perfil.id === usuarioService.getPerfilUnidade()) {
                                scope.usuarioLogado.isUnidade = true;
                            }
                        }
                    }

                    if (callback) {
                        callback();
                    }

                });
            });
        };

        scope.apenasUnidade = function () {
            return scope.usuarioLogado.isUnidade && !scope.usuarioLogado.isNucleo;
        };

        scope.getAllSecretarias = function () {
            service.getUnidadePeloIdLimitadoPorIdCategoria(scope.idOrgaoPai, scope.listaIdCategorias).then(function (response) {
                scope.orgaos = angular.copy(response.data);
            });
        };

        scope.getAllMacroprocessos = function () {
            service.getAllMacroprocessos().then(function (response) {
                scope._macroprocessos = angular.copy(response.data);
            });
        };

        scope.getAllCategorias = function () {
            service.getAllCategorias().then(function (response) {
                scope._categorias = angular.copy(response.data);
            });
        };

        scope.getIntegtidades = function () {
            scope._integridades = [
                {integridade: 'true', descricao: 'Sim'},
                {integridade: 'false', descricao: 'Não'}
            ];
        };

        scope.getNiveisRisco = function () {
            scope._niveisRisco = ['Pequeno', 'Moderado', 'Alto', 'Crítico'];
        };

        scope.gerarGrafico = function () {

            if (!scope.registro.secretarias) {
                scope.registro.secretarias = [];
            }

            service.gerarGrafico(scope.registro).then(function (response) {
                scope._grafico = angular.copy(response.data);
            });

            var monitoramento = angular.copy(scope.registro);
            monitoramento.riscosInerentes = [];

            service.graficoRiscoResidual(monitoramento).then(function (response) {
                scope._riscoResidual = {
                    data: [],
                    labels: ['Pequeno', 'Moderado', 'Alto', 'Crítico']
                };

                var riscos = angular.copy(response.data);

                for (var i = 0; i < riscos.length; i++) {
                    scope._riscoResidual.labels.push(riscos[i].nome);

                    if (scope.registro.niveisRisco.length == 0) {
                        scope._riscoResidual.data.push(riscos[i].quantidade);
                    } else {
                        if (scope.registro.niveisRisco.indexOf(riscos[i].nome) != -1) {
                            scope._riscoResidual.data.push(riscos[i].quantidade);
                        } else {
                            scope._riscoResidual.data.push(0);
                        }
                    }

                }

            });

            service.graficoCategoriaRisco(scope.registro).then(function (response) {
                scope._categoria = {
                    data: [],
                    labels: [],
                    itens: [],
                    colors: []
                };

                var categorias = angular.copy(response.data);

                for (var i = 0; i < categorias.length; i++) {
                    scope._categoria.labels.push(categorias[i].nome);
                    scope._categoria.data.push(categorias[i].quantidade);
                    scope._categoria.itens.push(i + 1);
                    scope._categoria.colors.push('#DF8244');
                }
            });
        };

        scope.gerarRelatorio = function () {
            service.gerarRelatorio(scope.registro).then(function (response) {
                baixarArquivo(response);
            });
        };

        function baixarArquivo(response) {
            var blob = new Blob([response.data], {type: response.headers('Content-Type')});
            var a = document.createElement('a');
            a.download = fileNameFromContentDisposition(response.headers('Content-Disposition'));
            a.target = '_blank';
            a.href = URL.createObjectURL(blob);
            a.click();
        }

        function fileNameFromContentDisposition(disposition) {
            if (disposition && disposition.indexOf('filename') !== -1) {
                var filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
                var matches = filenameRegex.exec(disposition);
                if (matches != null && matches[1]) {
                    return matches[1].replace(/['"]/g, '');
                }
            }
        }

        scope.init();
    }
})();
