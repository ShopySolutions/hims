(function() {
    'use strict';

    angular
        .module('hmisApp')
        .controller('FlatDetailController', FlatDetailController);

    FlatDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Flat', 'Profile', 'User', 'House', 'Floor'];

    function FlatDetailController($scope, $rootScope, $stateParams, previousState, entity, Flat, Profile, User, House, Floor) {
        var vm = this;

        vm.flat = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hmisApp:flatUpdate', function(event, result) {
            vm.flat = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
