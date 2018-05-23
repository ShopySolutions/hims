(function() {
    'use strict';

    angular
        .module('hmisApp')
        .controller('FlatDialogController', FlatDialogController);

    FlatDialogController.$inject = ['$timeout','$state', '$scope', '$stateParams', 'entity', 'Flat', 'Profile', 'User', 'House', 'Floor'];

    function FlatDialogController ($timeout,$state, $scope, $stateParams, entity, Flat, Profile, User, House, Floor) {
        var vm = this;

        vm.flat = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        // vm.profiles = Profile.query();
        // vm.users = User.query();
        vm.houses = House.query();
        vm.floors = Floor.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            //$uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.flat.id !== null) {
                Flat.update(vm.flat, onSaveSuccess, onSaveError);
            } else {
                Flat.save(vm.flat, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hmisApp:flatUpdate', result);
            $state.go('flat', {}, { reload: 'flat' });

            //$uibModalInstance.close(result);
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




        $scope.calculateTotalPrice=function (result) {
            console.log(result)

            if (result.flatFair != null && result.serviceCharge != null){
                vm.flat.totalFair= parseInt(result.flatFair)+parseInt(result.serviceCharge)
            }else if(result.flatFair != null){
                vm.flat.totalFair= parseInt(result.flatFair)

            }

        }



    }
})();
