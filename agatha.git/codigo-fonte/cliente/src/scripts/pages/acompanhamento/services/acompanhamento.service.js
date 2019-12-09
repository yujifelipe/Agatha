(function () {
    'use strict';
    angular.module('gestaoRiscosApp').factory('AcompanhamentoService', ['$http', service]);

    function service($http) {
        var baseUrl = '/gestaoriscos/api/acompanhamentos';

        var findAll = function (planoControleId) {
            return $http.get(baseUrl + '?planoControleId=' + planoControleId);
        };

        var create = function (registro) {
            return $http.post(baseUrl, registro);
        };

        var excluir = function (registroId) {
            return $http.delete(baseUrl + '/' + registroId);
        };

        var findByEventoRisco = function (registroId) {
            return $http.get('/gestaoriscos/api/processos/eventosrisco/' + registroId);
        };

        return {
            findAll: findAll,
            create: create,
            excluir: excluir,
            findByEventoRisco: findByEventoRisco
        };
    }
})();
