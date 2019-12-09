'use strict';
(function () {
    angular.module('gestaoRiscosApp').factory('OrgaoService', ['$http', service]);

    function service($http) {
        var orgaoUrl = '/gestaoriscos/api/siorg';

        var getSecretarias = function () {
            return $http.post(orgaoUrl + '/orgao/limitado-por-id-categoria', {idOrgaoPai: 2981, listaIdCategorias: [1, 4]});
        };

        return {
            getSecretarias: getSecretarias
        };
    }

}());
