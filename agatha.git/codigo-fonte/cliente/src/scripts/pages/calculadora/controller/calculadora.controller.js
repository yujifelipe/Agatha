/**
 * Created by Basis Tecnologia on 10/04/2017.
 */
(function () {
    'use strict';
    angular.module('gestaoRiscosApp').controller('CalculadoraController', ['$scope', '$mdToast', 'CalculadoraService', controller]);

    function controller($scope, $mdToast, service) {
        var scope = this;

        scope.init = function () {
            scope.registro = {};
            scope.getCalculadoraBase();
        };

        scope.getCalculadoraBase = function () {
            service.getCalculadoraBase().then(function (retorno) {
                scope.registro = retorno.data;
            });
        };

        scope.persistir = function (registro) {
            if ($scope.formRegistro.$valid) {
                if (verificaPesos()) {
                    service.update(registro).then(function () {
                        scope.init();
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

        scope.cancelar = function () {
            window.history.back();
        };

        function verificaPesos() {
            if ((scope.registro.impactos[0].peso +
                scope.registro.impactos[1].peso +
                scope.registro.impactos[2].peso +
                scope.registro.impactos[3].peso +
                scope.registro.impactos[4].peso +
                scope.registro.impactos[5].peso) > 100) {

                //noinspection JSUnresolvedFunction
                $mdToast.show(
                    $mdToast.simple()
                        .textContent('O peso total deve ser MENOR ou IGUAL a 100%!')
                        .hideDelay(3000)
                );

                return false;
            }
            return true;
        }

        scope.init();
    }
})();
