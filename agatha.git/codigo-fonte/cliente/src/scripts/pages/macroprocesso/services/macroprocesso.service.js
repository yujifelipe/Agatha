/**
 * Created by Basis Tecnologia on 10/06/2017.
 */
(function(){
    'use strict';
    angular.module('gestaoRiscosApp').factory('MacroprocessoService', ['$http', service]);

    function service($http){
        var baseUrl = '/gestaoriscos/api/macroprocessos';

        var findByFilter = function(filtros, query){
            var endPoint = baseUrl + "?page=" + filtros.page + "&size=" + filtros.size + "&descricao=";
            if(query.descricao != null){
                endPoint += query.descricao;
            }
            if(query.status != null){
                endPoint += "&status=" + query.status;
            }
            if(query.secretariaId != null){
                endPoint += "&secretariaId=" + query.secretariaId;
            }
            return $http.get(endPoint).success(function(data, status, headers){
                if(status == 200){
                    data.content = data;
                    data.totalElements = headers("x-total-count")
                }
            });
        };

        var getDescricaoBySearch = function(descricao){
            return $http.get(baseUrl + "/searchdescricao?descricao=" + descricao);
        };

        var findBy = function(registroId){
            return $http.get(baseUrl + "/" + registroId);
        };

        var create = function(registro){
            return $http.post(baseUrl, registro);
        };

        var update = function(registro){
            return $http.put(baseUrl, registro);
        };

        var alterarStatus = function(registroId){
            return $http.post(baseUrl, registroId);
        };

        var excluir = function(registroId){
            return $http.delete(baseUrl + "/" + registroId);
        };

        return {
            findByFilter: findByFilter,
            getDescricaoBySearch: getDescricaoBySearch,
            findBy: findBy,
            create: create,
            update: update,
            alterarStatus: alterarStatus,
            excluir: excluir
        };
    }
})();
