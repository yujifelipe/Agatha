(function () {
    angular.module('gestaoRiscosApp').directive('monitoramentoRisco', directive);

    function directive($mdToast) {
        return {
            restrict: 'EA',
            scope: {
                titulo: '@',
                riscos: '=',
                atualizar: '&'
            },
            templateUrl: 'scripts/app/directives/monitoramento-risco/monitoramento.risco.tmpl.html',
            link: function ($scope) {

                var scope = $scope;

                var init = function () {
                    scope.limparRisco();
                    scope.getFatores();
                    scope.getNiveis();

                };

                scope.adicionarRisco = function () {

                    if (!scope.fator) {
                        //noinspection JSUnresolvedFunction
                        $mdToast.show(
                            $mdToast.simple()
                                .textContent('Selecione um Fator!')
                                .hideDelay(3000)
                        );
                        return;
                    }

                    if (!scope.niveis || !scope.niveis.length) {
                        //noinspection JSUnresolvedFunction
                        $mdToast.show(
                            $mdToast.simple()
                                .textContent('Selecione pelo menos um nível!')
                                .hideDelay(3000)
                        );
                        return;
                    }

                    scope.riscos.push({
                        fator: angular.copy(scope.fator),
                        niveis: angular.copy(scope.niveis)
                    });

                    scope.limparRisco();
                    scope.atualizar();

                };

                scope.removerRisco = function (index) {
                    scope.riscos.splice(index, 1);
                    scope.atualizar();
                };


                scope.limparRisco = function f() {
                    scope.fator = '';
                    scope.niveis = [];
                };

                scope.getFatores = function () {
                    scope._fatores = [
                        {fator: 'Esforço de Gestão', descricao: 'Esforço de Gestão'},
                        {fator: 'Regulação', descricao: 'Regulação'},
                        {fator: 'Reputação', descricao: 'Reputação'},
                        {fator: 'Negócios / Serviços à Sociedade', descricao: 'Negócios / Serviços à Sociedade'},
                        {fator: 'Intervenção Hierárquica', descricao: 'Intervenção Hierárquica'},
                        {fator: 'Valor Orçamentário', descricao: 'Valor Orçamentário'}
                    ];
                };

                scope.getNiveis = function () {
                    scope._niveis = [
                        {nivel: '1', descricao: '1'},
                        {nivel: '2', descricao: '2'},
                        {nivel: '3', descricao: '3'},
                        {nivel: '4', descricao: '4'},
                        {nivel: '5', descricao: '5'}
                    ];
                };

                init();
            }
        };
    }

})();
