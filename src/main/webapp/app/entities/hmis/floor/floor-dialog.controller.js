(function() {
    'use strict';

    angular
        .module('hmisApp')
        .controller('FloorDialogController', FloorDialogController);

    FloorDialogController.$inject = ['$timeout', '$state','$scope', '$stateParams', 'entity', 'Floor', 'Profile', 'User', 'House'];

    function FloorDialogController ($timeout,$state, $scope, $stateParams, entity, Floor, Profile, User, House) {
        var vm = this;

        vm.floor = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        // vm.profiles = Profile.query();
        // vm.users = User.query();
        vm.houses = House.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            // $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.floor.id !== null) {
                Floor.update(vm.floor, onSaveSuccess, onSaveError);
            } else {
                Floor.save(vm.floor, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hmisApp:floorUpdate', result);
            // $uibModalInstance.close(result);
            $state.go('floor', {}, { reload: 'floor' });

            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createdate = false;
        vm.datePickerOpenStatus.updateDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
