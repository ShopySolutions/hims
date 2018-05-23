(function() {
    'use strict';

    angular
        .module('hmisApp')
        .controller('RentDialogController', RentDialogController);

    RentDialogController.$inject = ['$timeout','$state', '$scope', '$stateParams', 'entity', 'Rent', 'Profile', 'User', 'House', 'Floor', 'Flat'];

    function RentDialogController ($timeout,$state, $scope, $stateParams, entity, Rent, Profile, User, House, Floor, Flat) {
        var vm = this;

        vm.rent = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        /*vm.profiles = Profile.query();
        vm.users = User.query();*/
        vm.houses = House.query();
        vm.floors = Floor.query();
        vm.flats = Flat.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            // $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.rent.id !== null) {
                Rent.update(vm.rent, onSaveSuccess, onSaveError);
            } else {
                Rent.save(vm.rent, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hmisApp:rentUpdate', result);
            // $uibModalInstance.close(result);
            $state.go('rent', {}, { reload: 'rent' });

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
