(function () {
    'use strict';
    angular.module('gestaoRiscosApp').config(function ($stateProvider) {

        var PERFIL_COMITE = 1;
        var PERFIL_SUBCOMITE = 2;
        var PERFIL_NUCLEO = 3;
        var PERFIL_UNIDADE = 4;
        var PERFIL_GESTOR_PROCESSO = 5;
        var PERFIL_ANALISTA_RISCO = 6;

        $stateProvider
            .state('home', {
                url: '/',
                templateUrl: 'scripts/pages/home/view/home.tmpl.html',
                controller: 'HomeController as scope',
                ncyBreadcrumb: {
                    label: 'HOME'
                },
                data: {
                    authorization: false
                }
            })

            //Processo
            .state('processo', {
                url: '/processo',
                templateUrl: 'scripts/pages/processo/view/processo.tmpl.html',
                controller: 'ProcessoController as scope',
                abstract: true,
                data: {
                    authorization: false
                }
            })

            //Exportar Processo
            .state('exportar-processo', {
                url: '/processo/exportar/:id',
                templateUrl: 'scripts/pages/exportar-processo/view/exportar-processo.tmpl.html',
                controller: 'ExportarProcessoController as scope',
                ncyBreadcrumb: {
                    label: 'EXPORTAR PROCESSO',
                    parent: 'home'
                },
                data: {
                    authorization: false
                }
            })

            //Análise de Ambiente de Informações sobre Ambiente e sobre a Fixação de Objetivos
            .state('processo.analiseAmbiente', {
                url: '/analiseAmbiente',
                template: '<ui-view></ui-view>',
                abstract: true
            })

            //Incluir Análise de Ambiente de Informações sobre Ambiente e sobre a Fixação de Objetivos
            .state('processo.analiseAmbiente.incluir', {
                url: '/incluir',
                templateUrl: 'scripts/pages/analise-ambiente/view/analise-ambiente.tmpl.html',
                controller: 'AnaliseAmbienteController as scope',
                ncyBreadcrumb: {
                    label: 'ANÁLISE DE AMBIENTE E DE FIXAÇÃO DE OBJETIVOS',
                    parent: 'home'
                },
                data: {
                    authorization: true,
                    perfis: [PERFIL_UNIDADE, PERFIL_GESTOR_PROCESSO, PERFIL_ANALISTA_RISCO]
                }
            })

            //Consultar Análise de Ambiente de Informações sobre Ambiente e sobre a Fixação de Objetivos
            .state('processo.analiseAmbiente.consultar', {
                url: '/consultar/:id',
                templateUrl: 'scripts/pages/analise-ambiente/view/analise-ambiente.tmpl.html',
                controller: 'AnaliseAmbienteController as scope',
                ncyBreadcrumb: {
                    label: 'ANÁLISE DE AMBIENTE E DE FIXAÇÃO DE OBJETIVOS',
                    parent: 'home'
                },
                data: {
                    authorization: false
                }
            })

            //Alterar Análise de Ambiente de Informações sobre Ambiente e sobre a Fixação de Objetivos
            .state('processo.analiseAmbiente.alterar', {
                url: '/alterar/:id',
                templateUrl: 'scripts/pages/analise-ambiente/view/analise-ambiente.tmpl.html',
                controller: 'AnaliseAmbienteController as scope',
                ncyBreadcrumb: {
                    label: 'ANÁLISE DE AMBIENTE E DE FIXAÇÃO DE OBJETIVOS',
                    parent: 'home'
                },
                data: {
                    authorization: true,
                    perfis: [PERFIL_UNIDADE, PERFIL_GESTOR_PROCESSO, PERFIL_ANALISTA_RISCO]
                }
            })


            //Identificação
            .state('processo.identificacao', {
                url: '/identificacao',
                template: '<ui-view></ui-view>',
                abstract: true
            })

            //Consultar Identificação
            .state('processo.identificacao.consultar', {
                url: '/consultar/:id',
                templateUrl: 'scripts/pages/identificacao/view/identificacao.tmpl.html',
                controller: 'IdentificacaoController as scope',
                ncyBreadcrumb: {
                    label: 'IDENTIFICAÇÃO DE EVENTOS DE RISCO',
                    parent: 'processo.analiseAmbiente.consultar'
                },
                data: {
                    authorization: false
                }
            })

            //Alterar Identificação
            .state('processo.identificacao.alterar', {
                url: '/alterar/:id',
                templateUrl: 'scripts/pages/identificacao/view/identificacao.tmpl.html',
                controller: 'IdentificacaoController as scope',
                ncyBreadcrumb: {
                    label: 'IDENTIFICAÇÃO DE EVENTOS DE RISCO',
                    parent: 'processo.analiseAmbiente.alterar'
                },
                data: {
                    authorization: true,
                    perfis: [PERFIL_UNIDADE, PERFIL_GESTOR_PROCESSO, PERFIL_ANALISTA_RISCO]
                }
            })

            //Avaliação
            .state('processo.avaliacao', {
                url: '/avaliacao',
                templateUrl: 'scripts/pages/avaliacao/view/avaliacao.tmpl.html',
                controller: 'AvaliacaoController as scope',
                abstract: true
            })

            //Risco Inerente
            .state('processo.avaliacao.riscoInerente', {
                url: '/riscoInerente',
                template: '<ui-view></ui-view>',
                abstract: true
            })

            //Consultar Risco Inerente
            .state('processo.avaliacao.riscoInerente.consultar', {
                url: '/consultar/:id',
                templateUrl: 'scripts/pages/avaliacao-risco/view/avaliacao-risco-inerente.tmpl.html',
                controller: 'AvaliacaoRiscoController as scope',
                ncyBreadcrumb: {
                    label: 'AVALIAÇÃO DO RISCO INERENTE',
                    parent: 'processo.identificacao.consultar'
                },
                data: {
                    authorization: false
                }
            })

            //Alterar Risco Inerente
            .state('processo.avaliacao.riscoInerente.alterar', {
                url: '/alterar/:id',
                templateUrl: 'scripts/pages/avaliacao-risco/view/avaliacao-risco-inerente.tmpl.html',
                controller: 'AvaliacaoRiscoController as scope',
                ncyBreadcrumb: {
                    label: 'AVALIAÇÃO DO RISCO INERENTE',
                    parent: 'processo.identificacao.alterar'
                },
                data: {
                    authorization: true,
                    perfis: [PERFIL_UNIDADE, PERFIL_GESTOR_PROCESSO, PERFIL_ANALISTA_RISCO]
                }
            })

            //Controle Evento
            .state('processo.avaliacao.controleEvento', {
                url: '/controleEvento',
                template: '<ui-view></ui-view>',
                abstract: true
            })

            //Consultar Controle Evento
            .state('processo.avaliacao.controleEvento.consultar', {
                url: '/consultar/:id',
                templateUrl: 'scripts/pages/avaliacao-risco/view/avaliacao-controle-evento.tmpl.html',
                controller: 'AvaliacaoRiscoController as scope',
                ncyBreadcrumb: {
                    label: 'AVALIAÇÃO DOS CONTROLES EXISTENTES',
                    parent: 'processo.avaliacao.riscoInerente.consultar'
                },
                data: {
                    authorization: false
                }
            })

            //Alterar Controle Evento
            .state('processo.avaliacao.controleEvento.alterar', {
                url: '/alterar/:id',
                templateUrl: 'scripts/pages/avaliacao-risco/view/avaliacao-controle-evento.tmpl.html',
                controller: 'AvaliacaoRiscoController as scope',
                ncyBreadcrumb: {
                    label: 'AVALIAÇÃO DOS CONTROLES EXISTENTES',
                    parent: 'processo.avaliacao.riscoInerente.alterar'
                },
                data: {
                    authorization: true,
                    perfis: [PERFIL_UNIDADE, PERFIL_GESTOR_PROCESSO, PERFIL_ANALISTA_RISCO]
                }
            })

            //Risco Residual
            .state('processo.avaliacao.riscoResidual', {
                url: '/riscoResidual',
                template: '<ui-view></ui-view>',
                abstract: true
            })

            //Consultar Risco Residual
            .state('processo.avaliacao.riscoResidual.consultar', {
                url: '/consultar/:id',
                templateUrl: 'scripts/pages/avaliacao-risco/view/avaliacao-risco-residual.tmpl.html',
                controller: 'AvaliacaoRiscoController as scope',
                ncyBreadcrumb: {
                    label: 'AVALIAÇÃO DO RISCO RESIDUAL',
                    parent: 'processo.avaliacao.controleEvento.consultar'
                },
                data: {
                    authorization: false
                }
            })

            //Alterar Risco Residual
            .state('processo.avaliacao.riscoResidual.alterar', {
                url: '/alterar/:id',
                templateUrl: 'scripts/pages/avaliacao-risco/view/avaliacao-risco-residual.tmpl.html',
                controller: 'AvaliacaoRiscoController as scope',
                ncyBreadcrumb: {
                    label: 'AVALIAÇÃO DO RISCO RESIDUAL',
                    parent: 'processo.avaliacao.controleEvento.alterar'
                },
                data: {
                    authorization: true,
                    perfis: [PERFIL_UNIDADE, PERFIL_GESTOR_PROCESSO, PERFIL_ANALISTA_RISCO]
                }
            })

            //Resposta ao Risco
            .state('processo.respostaRisco', {
                url: '/respostaRisco',
                template: '<ui-view></ui-view>',
                abstract: true
            })

            //Consultar Resposta ao Risco
            .state('processo.respostaRisco.consultar', {
                url: '/consultar/:id',
                templateUrl: 'scripts/pages/resposta/view/resposta.listar.tmpl.html',
                controller: 'RespostaController as scope',
                ncyBreadcrumb: {
                    label: 'RESPOSTA A RISCO',
                    parent: 'processo.avaliacao.riscoResidual.consultar'
                },
                data: {
                    authorization: false
                }
            })

            //Alterar Resposta ao risco
            .state('processo.respostaRisco.editar', {
                url: '/editar/:id?:eventoRiscoId',
                templateUrl: 'scripts/pages/resposta/view/resposta.detalhar.tmpl.html',
                controller: 'RespostaController as scope',
                ncyBreadcrumb: {
                    label: 'ALTERAR RESPOSTA AO RISCO',
                    parent: 'processo.respostaRisco.alterar'
                },
                data: {
                    authorization: true,
                    perfis: [PERFIL_UNIDADE, PERFIL_GESTOR_PROCESSO, PERFIL_ANALISTA_RISCO]
                }
            })

            //Alterar Resposta ao Risco
            .state('processo.respostaRisco.alterar', {
                url: '/alterar/:id',
                templateUrl: 'scripts/pages/resposta/view/resposta.listar.tmpl.html',
                controller: 'RespostaController as scope',
                ncyBreadcrumb: {
                    label: 'RESPOSTA A RISCO',
                    parent: 'processo.avaliacao.riscoResidual.alterar'
                },
                data: {
                    authorization: true,
                    perfis: [PERFIL_UNIDADE, PERFIL_GESTOR_PROCESSO, PERFIL_ANALISTA_RISCO]
                }
            })

            //Plano de Controle
            .state('processo.planoControle', {
                url: '/planoControle',
                template: '<ui-view></ui-view>',
                abstract: true
            })

            .state('processo.planoControle.editar', {
                url: '/editar/:id?:eventoRiscoId',
                templateUrl: 'scripts/pages/plano-controle/view/plano-controle.alterar.tmpl.html',
                controller: 'PlanoControleController as scope',
                ncyBreadcrumb: {
                    label: 'ALTERAR PLANO DE CONTROLE',
                    parent: 'processo.planoControle.alterar'
                },
                data: {
                    authorization: true,
                    perfis: [PERFIL_UNIDADE, PERFIL_GESTOR_PROCESSO, PERFIL_ANALISTA_RISCO]
                }
            })

            //Consultar Plano de Controle
            .state('processo.planoControle.consultar', {
                url: '/consultar/:id',
                templateUrl: 'scripts/pages/plano-controle/view/plano-controle.listar.tmpl.html',
                controller: 'PlanoControleController as scope',
                ncyBreadcrumb: {
                    label: 'PLANO DE CONTROLE',
                    parent: 'processo.respostaRisco.consultar'
                },
                data: {
                    authorization: false
                }
            })


            //Alterar Plano de Controle
            .state('processo.planoControle.alterar', {
                url: '/alterar/:id',
                templateUrl: 'scripts/pages/plano-controle/view/plano-controle.listar.tmpl.html',
                controller: 'PlanoControleController as scope',
                ncyBreadcrumb: {
                    label: 'PLANO DE CONTROLE',
                    parent: 'processo.respostaRisco.alterar'
                },
                data: {
                    authorization: true,
                    perfis: [PERFIL_UNIDADE, PERFIL_GESTOR_PROCESSO, PERFIL_ANALISTA_RISCO]
                }
            })

            //AÇÃO DE ACOMPANHAMENTO
            .state('acompanhamento', {
                url: '/acompanhamento/:id?:eventoRiscoId',
                templateUrl: 'scripts/pages/acompanhamento/view/acompanhamento.tmpl.html',
                controller: 'AcompanhamentoController as scope',
                ncyBreadcrumb: {
                    label: 'AÇÕES ACOMPANHAMENTO',
                    parent: 'processo.planoControle.alterar'
                },
                data: {
                    authorization: true,
                    perfis: [PERFIL_UNIDADE, PERFIL_GESTOR_PROCESSO, PERFIL_ANALISTA_RISCO]
                }
            })

            //Validação
            .state('processo.validacao', {
                url: '/validacao',
                template: '<ui-view></ui-view>',
                abstract: true
            })

            //Alterar Validação
            .state('processo.validacao.alterar', {
                url: '/alterar/:id',
                templateUrl: 'scripts/pages/validacao/view/validacao.detalhar.tmpl.html',
                controller: 'ValidacaoController as scope',
                ncyBreadcrumb: {
                    label: 'VALIDAÇÃO',
                    parent: 'processo.planoControle.alterar'
                },
                data: {
                    authorization: true,
                    perfis: [PERFIL_GESTOR_PROCESSO]
                }
            })

            //Consultar Validação
            .state('processo.validacao.consultar', {
                url: '/consultar/:id',
                templateUrl: 'scripts/pages/validacao/view/validacao.detalhar.tmpl.html',
                controller: 'ValidacaoController as scope',
                ncyBreadcrumb: {
                    label: 'VALIDAÇÃO',
                    parent: 'processo.planoControle.consultar'
                },
                data: {
                    authorization: false
                }
            })

            //Eventos de Risco
            .state('eventoRisco', {
                url: '/eventoRisco',
                template: '<ui-view></ui-view>',
                abstract: true
            })

            //Criar Eventos de Risco
            .state('eventoRisco.criar', {
                url: '/criar/:id',
                templateUrl: 'scripts/pages/evento-risco/view/evento-risco.tmpl.html',
                controller: 'EventoRiscoController as scope',
                ncyBreadcrumb: {
                    label: 'CRIAR EVENTO DE RISCO',
                    parent: 'processo.identificacao.alterar'
                },
                data: {
                    authorization: true,
                    perfis: [PERFIL_UNIDADE, PERFIL_GESTOR_PROCESSO, PERFIL_ANALISTA_RISCO]
                }
            })

            //Alterar Eventos de Risco
            .state('eventoRisco.alterar', {
                url: '/alterar/:id?:eventoRiscoId',
                templateUrl: 'scripts/pages/evento-risco/view/evento-risco.tmpl.html',
                controller: 'EventoRiscoController as scope',
                ncyBreadcrumb: {
                    label: 'ALTERAR EVENTO DE RISCO',
                    parent: 'processo.identificacao.alterar'
                },
                data: {
                    authorization: true,
                    perfis: [PERFIL_UNIDADE, PERFIL_GESTOR_PROCESSO, PERFIL_ANALISTA_RISCO]
                }

            })

            //Alterar Controle de Evento
            .state('controleEvento', {
                url: '/controleEvento/:id?:eventoRiscoId',
                templateUrl: 'scripts/pages/controle-evento/view/controle-evento.tmpl.html',
                controller: 'ControleEventoController as scope',
                ncyBreadcrumb: {
                    label: 'ALTERAR CONTROLE DE EVENTOS',
                    parent: 'processo.avaliacao.controleEvento.alterar'
                },
                data: {
                    authorization: true,
                    perfis: [PERFIL_UNIDADE, PERFIL_GESTOR_PROCESSO, PERFIL_ANALISTA_RISCO]
                }
            })

            //Calcular risco inerente
            .state('riscoInerente', {
                url: '/riscoInerente/:id',
                templateUrl: 'scripts/pages/risco-inerente/view/risco-inerente.tmpl.html',
                controller: 'RiscoInerenteController as scope',
                ncyBreadcrumb: {
                    label: 'CALCULAR MAPA DE RISCO INERENTE',
                    parent: 'processo.avaliacao.riscoInerente.alterar'
                },
                data: {
                    authorization: true,
                    perfis: [PERFIL_UNIDADE, PERFIL_GESTOR_PROCESSO, PERFIL_ANALISTA_RISCO]
                }
            })

            //Calcular risco inerente Probablidade
            .state('riscoInerente.probabilidade', {
                url: '/riscoInerente/probabilidade/:id',
                templateUrl: 'scripts/pages/risco-inerente/view/risco-inerente-probabilidade.tmpl.html',
                controller: 'RiscoInerenteController as scope',
                ncyBreadcrumb: {
                    label: 'CALCULAR MAPA DE RISCO INERENTE',
                    parent: 'processo.avaliacao.riscoInerente.alterar'
                },
                data: {
                    authorization: true,
                    perfis: [PERFIL_UNIDADE, PERFIL_GESTOR_PROCESSO, PERFIL_ANALISTA_RISCO]
                }
            })

            //Calcular risco inerente
            .state('riscoInerente.impacto', {
                url: '/riscoInerente/impacto/:id',
                templateUrl: 'scripts/pages/risco-inerente/view/risco-inerente-impacto.tmpl.html',
                controller: 'RiscoInerenteController as scope',
                ncyBreadcrumb: {
                    label: 'CALCULAR MAPA DE RISCO INERENTE',
                    parent: 'processo.avaliacao.riscoInerente.alterar'
                },
                data: {
                    authorization: true,
                    perfis: [PERFIL_UNIDADE, PERFIL_GESTOR_PROCESSO, PERFIL_ANALISTA_RISCO]
                }
            })

            //Calcular risco residual
            .state('riscoResidual', {
                url: '/riscoResidual/:id',
                templateUrl: 'scripts/pages/risco-residual/view/risco-residual.tmpl.html',
                controller: 'RiscoResidualController as scope',
                ncyBreadcrumb: {
                    label: 'CALCULAR MAPA DE RISCO RESIDUAL',
                    parent: 'processo.avaliacao.riscoResidual.alterar'
                },
                data: {
                    authorization: true,
                    perfis: [PERFIL_UNIDADE, PERFIL_GESTOR_PROCESSO, PERFIL_ANALISTA_RISCO]
                }
            })

            //Calcular risco residual Probablidade
            .state('riscoResidual.probabilidade', {
                url: '/riscoResidual/probabilidade/:id',
                templateUrl: 'scripts/pages/risco-residual/view/risco-residual-probabilidade.tmpl.html',
                controller: 'RiscoResidualController as scope',
                ncyBreadcrumb: {
                    label: 'CALCULAR MAPA DE RISCO RESIDUAL',
                    parent: 'processo.avaliacao.riscoResidual.alterar'
                },
                data: {
                    authorization: true,
                    perfis: [PERFIL_UNIDADE, PERFIL_GESTOR_PROCESSO, PERFIL_ANALISTA_RISCO]
                }
            })

            //Calcular risco residual
            .state('riscoResidual.impacto', {
                url: '/riscoResidual/impacto/:id',
                templateUrl: 'scripts/pages/risco-residual/view/risco-residual-impacto.tmpl.html',
                controller: 'RiscoResidualController as scope',
                ncyBreadcrumb: {
                    label: 'CALCULAR MAPA DE RISCO RESIDUAL',
                    parent: 'processo.avaliacao.riscoResidual.alterar'
                },
                data: {
                    authorization: true,
                    perfis: [PERFIL_UNIDADE, PERFIL_GESTOR_PROCESSO, PERFIL_ANALISTA_RISCO]
                }
            })


            //Plano de Controle
            .state('planoControle', {
                url: '/planoControle',
                template: '<ui-view></ui-view>',
                abstract: true
            })

            .state('planoControle.detalhar', {
                url: '/detalhar/:id?:eventoRiscoId',
                templateUrl: 'scripts/pages/plano-controle/view/plano-controle.detalhar.tmpl.html',
                controller: 'PlanoControleController as scope',
                ncyBreadcrumb: {
                    label: 'DETALHAR PLANO DE CONTROLE',
                    parent: 'processo.planoControle.alterar'
                },
                data: {
                    authorization: false
                }
            })

            //Manter Causas de Eventos de Risco
            .state('manterCausaEventoRisco', {
                url: '/manterCausaEventoRisco',
                templateUrl: 'scripts/pages/causa-evento-risco/view/causa-evento-risco.tmpl.html',
                controller: 'CausaEventoRiscoController as scope',
                ncyBreadcrumb: {
                    label: 'MANTER CAUSA DE EVENTO DE RISCO',
                    parent: 'home'
                },
                data: {
                    authorization: true,
                    perfis: [PERFIL_NUCLEO]
                }
            })

            //Manter Consequências de Eventos de Risco
            .state('manterConsequenciaEventoRisco', {
                url: '/manterConsequenciaEventoRisco',
                templateUrl: 'scripts/pages/consequencia-evento-risco/view/consequencia-evento-risco.tmpl.html',
                controller: 'ConsequenciaEventoRiscoController as scope',
                ncyBreadcrumb: {
                    label: 'MANTER CONSEQUÊNCIA DE EVENTO DE RISCO',
                    parent: 'home'
                },
                data: {
                    authorization: true,
                    perfis: [PERFIL_NUCLEO]
                }
            })

            //Manter Eventos de Risco
            .state('manterEventoRisco', {
                url: '/manterEventoRisco',
                templateUrl: 'scripts/pages/evento/view/evento.tmpl.html',
                controller: 'EventoController as scope',
                ncyBreadcrumb: {
                    label: 'MANTER EVENTO DE RISCO',
                    parent: 'home'
                },
                data: {
                    authorization: true,
                    perfis: [PERFIL_NUCLEO]
                }
            })

            //Manter Controles de Risco
            .state('manterControle', {
                url: '/manterControle',
                templateUrl: 'scripts/pages/controle/view/controle.tmpl.html',
                controller: 'ControleController as scope',
                ncyBreadcrumb: {
                    label: 'MANTER CONTROLE',
                    parent: 'home'
                },
                data: {
                    authorization: true,
                    perfis: [PERFIL_NUCLEO]
                }
            })

            //Manter Categorias de Risco
            .state('manterCategoria', {
                url: '/manterCategoria',
                templateUrl: 'scripts/pages/categoria/view/categoria.tmpl.html',
                controller: 'CategoriaController as scope',
                ncyBreadcrumb: {
                    label: 'MANTER CATEGORIA DE RISCO',
                    parent: 'home'
                },
                data: {
                    authorization: true,
                    perfis: [PERFIL_NUCLEO]
                }
            })

            //Manter Desenho de Controle
            .state('manterDesenho', {
                url: '/manterDesenho',
                templateUrl: 'scripts/pages/desenho/view/desenho.tmpl.html',
                controller: 'DesenhoController as scope',
                ncyBreadcrumb: {
                    label: 'MANTER DESENHO DE CONTROLE',
                    parent: 'home'
                },
                data: {
                    authorization: true,
                    perfis: [PERFIL_NUCLEO]
                }
            })

            //Manter Operação de Controle
            .state('manterOperacao', {
                url: '/manterOperacao',
                templateUrl: 'scripts/pages/operacao/view/operacao.tmpl.html',
                controller: 'OperacaoController as scope',
                ncyBreadcrumb: {
                    label: 'MANTER OPERAÇÃO DE CONTROLE',
                    parent: 'home'
                },
                data: {
                    authorization: true,
                    perfis: [PERFIL_NUCLEO]
                }
            })

            //Manter Macroprocesso
            .state('manterMacroprocesso', {
                url: '/manterMacroprocesso',
                templateUrl: 'scripts/pages/macroprocesso/view/macroprocesso.tmpl.html',
                controller: 'MacroprocessoController as scope',
                ncyBreadcrumb: {
                    label: 'MANTER MACROPROCESSO',
                    parent: 'home'
                },
                data: {
                    authorization: true,
                    perfis: [PERFIL_NUCLEO]
                }
            })

            //Manter Calculadora
            .state('manterCalculadora', {
                url: '/manterCalculadora',
                templateUrl: 'scripts/pages/calculadora/view/calculadora.tmpl.html',
                controller: 'CalculadoraController as scope',
                ncyBreadcrumb: {
                    label: 'MANTER CALCULADORA',
                    parent: 'home'
                },
                data: {
                    authorization: true,
                    perfis: [PERFIL_NUCLEO]
                }
            })

            //Manter Glossário
            .state('manterGlossario', {
                url: '/manterGlossario',
                templateUrl: 'scripts/pages/glossario/view/glossario.tmpl.html',
                controller: 'GlossarioController as scope',
                ncyBreadcrumb: {
                    label: 'MANTER GLOSSÁRIO',
                    parent: 'home'
                },
                data: {
                    authorization: true,
                    perfis: [PERFIL_NUCLEO]
                }
            })

            //Gerenciar Permissões
            .state('gerenciarPermissoes', {
                url: '/gerenciarPermissoes',
                abstract: true,
                template: '<ui-view/>'
            })
            .state('gerenciarPermissoes.list', {
                url: '/',
                templateUrl: 'scripts/pages/gerenciar-permissoes/view/gerenciar-permissoes-list.tmpl.html',
                controller: 'GerenciarPermissoesController as scope',
                ncyBreadcrumb: {
                    label: 'GERENCIAR PERMISSÕES',
                    parent: 'home'
                },
                data: {
                    authorization: true,
                    perfis: [PERFIL_NUCLEO, PERFIL_UNIDADE]
                }
            })
            .state('gerenciarPermissoes.create', {
                url: '/cadastrar',
                templateUrl: 'scripts/pages/gerenciar-permissoes/view/gerenciar-permissoes-detail.tmpl.html',
                controller: 'GerenciarPermissoesController as scope',
                ncyBreadcrumb: {
                    label: 'INCLUIR USUÁRIO',
                    parent: 'gerenciarPermissoes.list'
                },
                data: {
                    authorization: true,
                    perfis: [PERFIL_NUCLEO, PERFIL_UNIDADE]
                }
            })

            .state('gerenciarPermissoes.detail', {
                url: '/alterar/:id',
                templateUrl: 'scripts/pages/gerenciar-permissoes/view/gerenciar-permissoes-detail.tmpl.html',
                controller: 'GerenciarPermissoesController as scope',
                ncyBreadcrumb: {
                    label: 'ALTERAR PERMISSÕES',
                    parent: 'gerenciarPermissoes.list'
                },
                data: {
                    authorization: true,
                    perfis: [PERFIL_NUCLEO, PERFIL_UNIDADE]
                }
            })

            //Taxonomia
            .state('taxonomia', {
                url: '/taxonomia',
                abstract: true,
                template: '<ui-view/>'
            })

            //Taxonomia
            .state('taxonomia.listar', {
                url: '/',
                templateUrl: 'scripts/pages/taxonomia/view/taxonomia.tmpl.html',
                controller: 'TaxonomiaController as scope',
                ncyBreadcrumb: {
                    label: 'TAXONOMIA',
                    parent: 'home'
                },
                data: {
                    authorization: true,
                    perfis: [PERFIL_NUCLEO]
                }
            })

            //Agrupamento
            .state('taxonomia.agrupamento', {
                url: '/agrupamento',
                abstract: true,
                template: '<ui-view/>'
            })

            //Agrupamento
            .state('taxonomia.agrupamento.listar', {
                url: '/',
                templateUrl: 'scripts/pages/agrupamento-taxonomia/view/agrupamento-taxonomia.listar.tmpl.html',
                controller: 'AgrupamentoTaxonomiaController as scope',
                ncyBreadcrumb: {
                    label: 'AGRUPAMENTO TAXONOMIA',
                    parent: 'taxonomia.listar'
                },
                data: {
                    authorization: true,
                    perfis: [PERFIL_NUCLEO]
                }
            })

            //Agrupamento
            .state('taxonomia.agrupamento.detalhar', {
                url: '/inserir',
                templateUrl: 'scripts/pages/agrupamento-taxonomia/view/agrupamento-taxonomia.detalhar.tmpl.html',
                controller: 'AgrupamentoTaxonomiaController as scope',
                ncyBreadcrumb: {
                    label: 'AGRUPAMENTO TAXONOMIA',
                    parent: 'taxonomia.listar'
                },
                data: {
                    authorization: true,
                    perfis: [PERFIL_NUCLEO]
                }
            })

            //Meus dados
            .state('meus-dados', {
                url: '/meus-dados',
                templateUrl: 'scripts/pages/meus-dados/view/meus-dados.tmpl.html',
                controller: 'MeusDadosController as scope',
                ncyBreadcrumb: {
                    label: 'MEUS DADOS',
                    parent: 'home'
                },
                data: {
                    authorization: false
                }
            })

            //Usuario
            .state('usuario-sem-permissao', {
                url: '/usuario-sem-permissao',
                templateUrl: 'scripts/pages/usuario/usuario-sem-permissao.tmpl.html',
                ncyBreadcrumb: {
                    label: 'USUÁRIO SEM PERMISSÃO',
                    parent: 'home'
                },
                data: {
                    authorization: false
                }
            })

            //Usuario
            .state('usuario-nao-autenticado', {
                url: '/usuario-nao-autenticado',
                templateUrl: 'scripts/pages/usuario/usuario-nao-autenticado.tmpl.html',
                ncyBreadcrumb: {
                    label: 'USUÁRIO NÃO AUTENTICADO',
                    parent: 'home'
                },
                data: {
                    authorization: false
                }
            })

            //Gerar Gráfico de Monitoramento
            .state('monitoramento', {
                url: '/monitoramento',
                templateUrl: 'scripts/pages/monitoramento/view/monitoramento.tmpl.html',
                controller: 'MonitoramentoController as scope',
                ncyBreadcrumb: {
                    label: 'MONITORAMENTO',
                    parent: 'home'
                },
                data: {
                    authorization: true,
                    perfis: [PERFIL_COMITE, PERFIL_SUBCOMITE, PERFIL_NUCLEO, PERFIL_UNIDADE, PERFIL_GESTOR_PROCESSO, PERFIL_ANALISTA_RISCO]
                }
            });
    });

})();
