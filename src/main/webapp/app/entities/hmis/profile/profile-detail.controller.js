(function() {
    'use strict';

    angular
        .module('hmisApp')
        .controller('ProfileDetailController', ProfileDetailController);

    ProfileDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Profile', 'Country', 'State', 'City', 'User','getCurrentUserProfile'];

    function ProfileDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Profile, Country, State, City, User,getCurrentUserProfile) {
        var vm = this;

        vm.profile = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('hmisApp:profileUpdate', function(event, result) {
            vm.profile = result;
        });
        $scope.$on('$destroy', unsubscribe);

        if(!$stateParams.id) {
            getCurrentUserProfile.get({}, function (result) {
                console.log(result)
                vm.profile=result
            })
        }else if($stateParams.id){
            Profile.get({id : $stateParams.id},function (result) {
                vm.profile=result

            })
        }
    }
})();
