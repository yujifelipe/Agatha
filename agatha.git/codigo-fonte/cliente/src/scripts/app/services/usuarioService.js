/**
 * Created by Basis Tecnologia on 24/08/2016.
 */
'use strict';
(function () {

    angular.module('gestaoRiscosApp').factory('usuarioService', ['$http', '$rootScope', 'comumService', service]);

    function service($http, $rootScope) {

        var urlUsuario = 'gestaoriscos/api/usuarios/usuario-logado';

        var ID_PERFIL_NUCLEO = 3;
        var ID_PERFIL_UNIDADE = 4;
        var ID_PERFIL_GESTOR = 5;
        var ID_PERFIL_ANALISTA = 6;

        var getUsuarioLogado = function () {
            return $http.get(urlUsuario);
        };

        var getMenuUsuario = function () {

            var menuProcesso = {
                descricaoResumida: 'PROCESSO',
                url: '',
                nos: [
                    {
                        descricaoResumida: 'Gerenciar Processo',
                        url: '',
                        nos: [
                            {
                                descricaoResumida: 'GERENCIAR',
                                url: '/#/',
                                nos: []
                            },
                            {
                                descricaoResumida: 'RELATÓRIO',
                                url: '/#/monitoramento',
                                nos: []
                            }
                        ]
                    }
                ]
            };
            var menuPermissao = {
                descricaoResumida: 'PERMISSÃO',
                url: '',
                nos: [
                    {
                        descricaoResumida: 'Gerenciar Permissão',
                        url: '',
                        nos: [
                            {
                                descricaoResumida: 'GERENCIAR',
                                url: '/#/gerenciarPermissoes/',
                                nos: []
                            }
                        ]
                    }
                ]
            };
            var menuNucleo = {
                descricaoResumida: 'NÚCLEO',
                url: '',
                nos: [
                    {
                        descricaoResumida: 'Gerenciar Eventos de Risco',
                        url: '',
                        nos: [
                            // {
                            //     descricaoResumida: 'EVENTO DE RISCO',
                            //     url: '/#/manterEventoRisco',
                            //     nos: []
                            // },
                            // {
                            //     descricaoResumida: 'CAUSAS DE EVENTOS DE RISCO',
                            //     url: '/#/manterCausaEventoRisco',
                            //     nos: []
                            // },
                            // {
                            //     descricaoResumida: 'CONSEQUÊNCIA DE EVENTOS DE RISCO',
                            //     url: '/#/manterConsequenciaEventoRisco',
                            //     nos: []
                            // },
                            {
                                descricaoResumida: 'CATEGORIA E NATUREZA DE RISCO',
                                url: '/#/manterCategoria',
                                nos: []
                            },
                            {
                                descricaoResumida: 'MANTER CALCULADORA',
                                url: '/#/manterCalculadora',
                                nos: []
                            },
                            // {
                            //     descricaoResumida: 'CONTROLE DE RISCO',
                            //     url: '/#/manterControle',
                            //     nos: []
                            // },
                            {
                                descricaoResumida: 'DESENHO DE CONTROLE',
                                url: '/#/manterDesenho',
                                nos: []
                            },
                            {
                                descricaoResumida: 'OPERAÇÃO DE CONTROLE',
                                url: '/#/manterOperacao',
                                nos: []
                            },
                            // {
                            //     descricaoResumida: 'TAXONOMIA',
                            //     url: '/#/taxonomia/',
                            //     nos: []
                            // },
                            {
                                descricaoResumida: 'GLOSSÁRIO',
                                url: '/#/manterGlossario',
                                nos: []
                            },
                            {
                                descricaoResumida: 'MACROPROCESSO',
                                url: '/#/manterMacroprocesso',
                                nos: []
                            }
                        ]
                    }
                ]
            };

            getUsuarioLogado().success(function (usuario) {

                var nucleo = false;
                var unidade = false;

                for (var i = 0; i < usuario.permissoes.length; i++) {
                    var permissao = usuario.permissoes[i];

                    if (!permissao.excluido) {
                        if (permissao.perfil.id === ID_PERFIL_NUCLEO) {
                            nucleo = true;
                        } else if (permissao.perfil.id === ID_PERFIL_UNIDADE) {
                            unidade = true;
                        }
                    }
                }

                if (nucleo) {
                    $rootScope.menuAcessos = [menuProcesso, menuPermissao, menuNucleo];
                } else if (unidade) {
                    $rootScope.menuAcessos = [menuProcesso, menuPermissao];
                } else {
                    $rootScope.menuAcessos = [menuProcesso];
                }
            });

        };

        var findAllGlossarios = function () {
            return $http.get('/gestaoriscos/api/glossarios/findall');
        };

        var authenticate = function (toState) {
            if (!toState || !toState.data
                || !toState.data.perfis || toState.data.perfis.length === 0) {
                console.log('Autorizado por perfis vazios');
                return true;

            }

            return getUsuarioLogado().then(function (resolve) {
                if (resolve.data.permissoes.filter(function (permissao) {
                    if (!permissao.excluido
                        && toState.data.perfis.filter(function (idPerfil) {
                            if (idPerfil === permissao.perfil.id) {
                                return idPerfil;
                            }
                        }).length > 0) {
                        return permissao;
                    }
                }).length > 0) {
                    return true;
                }
                return false;
            });
        };

        var getPermissao = function (cpf) {
            return $http.get('/gestaoriscos/api/processos/permissao?cpf=' + cpf);
        };


        var getPerfilNucleo = function () {
            return ID_PERFIL_NUCLEO;
        };

        var getPerfilUnidade = function () {
            return ID_PERFIL_UNIDADE;
        };

        var getPerfilGestor = function () {
            return ID_PERFIL_GESTOR;
        };

        var getPerfilAnalista = function () {
            return ID_PERFIL_ANALISTA;
        };

        return {
            getUsuarioLogado: getUsuarioLogado,
            getMenuUsuario: getMenuUsuario,
            findAllGlossarios: findAllGlossarios,
            authenticate: authenticate,
            getPermissao: getPermissao,
            getPerfilNucleo: getPerfilNucleo,
            getPerfilUnidade: getPerfilUnidade,
            getPerfilGestor: getPerfilGestor,
            getPerfilAnalista: getPerfilAnalista
        };
    }

}());
