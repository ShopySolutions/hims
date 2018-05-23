(function() {
    'use strict';

    angular
        .module('hmisApp')
        .controller('RentDetailController', RentDetailController);

    RentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Rent', 'Profile', 'User', 'House', 'Floor', 'Flat'];

    function RentDetailController($scope, $rootScope, $stateParams, previousState, entity, Rent, Profile, User, House, Floor, Flat) {
        var vm = this;

        vm.rent = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hmisApp:rentUpdate', function(event, result) {
            vm.rent = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
