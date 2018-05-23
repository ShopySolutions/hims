(function() {
    'use strict';

    angular
        .module('hmisApp')
        .controller('FloorDetailController', FloorDetailController);

    FloorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Floor', 'Profile', 'User', 'House'];

    function FloorDetailController($scope, $rootScope, $stateParams, previousState, entity, Floor, Profile, User, House) {
        var vm = this;

        vm.floor = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hmisApp:floorUpdate', function(event, result) {
            vm.floor = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
