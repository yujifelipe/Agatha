/**
 * Created by Basis Tecnologia on 19/09/2018.
 */
(function () {
    'use strict';
    angular.module('gestaoRiscosApp').factory('MonitoramentoService', ['$http', service]);

    function service($http) {
        var baseUrl = '/gestaoriscos/api/monitoramentos';
        var orgaoUrl = '/gestaoriscos/api/siorg';

        var findByFilter = function (filtros) {
            var endPoint = baseUrl + '?page=' + filtros.page + '&size=' + filtros.size;
            return $http.get(endPoint).success(function (data, status, headers) {
                if (status == 200) {
                    data.content = data;
                    data.totalElements = headers('x-total-count');
                }
            });
        };

        var findBy = function (registroId) {
            return $http.get(baseUrl + '/' + registroId);
        };

        var create = function (registro) {
            return $http.post(baseUrl, registro);
        };

        var update = function (registro) {
            return $http.put(baseUrl, registro);
        };

        var excluir = function (registroId) {
            return $http.delete(baseUrl + '/' + registroId);
        };

        var getUnidadePeloIdLimitadoPorIdCategoria = function (idOrgaoPai, listaIdCategorias) {
            return $http.post(orgaoUrl + '/orgao/limitado-por-id-categoria', {idOrgaoPai: idOrgaoPai, listaIdCategorias: listaIdCategorias});
        };

        var getAllMacroprocessos = function () {
            return $http.get(baseUrl + '/macroprocessos');
        };

        var getAllCategorias = function () {
            return $http.get(baseUrl + '/categorias');
        };

        var gerarGrafico = function (registro) {
            return $http.post(baseUrl + '/gerar-grafico', registro);
        };

        var graficoRiscoResidual = function (registro) {
            return $http.post(baseUrl + '/grafico-risco-residual', registro);
        };

        var graficoCategoriaRisco = function (registro) {
            return $http.post(baseUrl + '/grafico-categoria-risco', registro);
        };

        var gerarRelatorio = function (registro) {
            return $http.post(baseUrl + '/gerar-relatorio', registro, { responseType: 'blob' });
        };

        return {
            findByFilter: findByFilter,
            findBy: findBy,
            create: create,
            update: update,
            excluir: excluir,
            getUnidadePeloIdLimitadoPorIdCategoria: getUnidadePeloIdLimitadoPorIdCategoria,
            getAllMacroprocessos: getAllMacroprocessos,
            getAllCategorias: getAllCategorias,
            gerarGrafico: gerarGrafico,
            graficoRiscoResidual: graficoRiscoResidual,
            graficoCategoriaRisco: graficoCategoriaRisco,
            gerarRelatorio: gerarRelatorio
        };
    }
})();
