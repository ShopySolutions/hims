(function() {
    'use strict';

    angular
        .module('hmisApp')
        .controller('BillingDetailController', BillingDetailController);

    BillingDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Billing', 'Profile', 'User', 'House', 'Floor', 'Flat', 'Rent'];

    function BillingDetailController($scope, $rootScope, $stateParams, previousState, entity, Billing, Profile, User, House, Floor, Flat, Rent) {
        var vm = this;

        vm.billing = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hmisApp:billingUpdate', function(event, result) {
            vm.billing = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
