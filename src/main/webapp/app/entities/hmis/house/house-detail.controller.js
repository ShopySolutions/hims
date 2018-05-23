(function() {
    'use strict';

    angular
        .module('hmisApp')
        .controller('HouseDetailController', HouseDetailController);

    HouseDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'House', 'Country', 'State', 'City', 'Profile', 'User'];

    function HouseDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, House, Country, State, City, Profile, User) {
        var vm = this;

        vm.house = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('hmisApp:houseUpdate', function(event, result) {
            vm.house = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
