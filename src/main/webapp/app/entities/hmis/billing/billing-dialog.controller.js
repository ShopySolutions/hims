(function() {
    'use strict';

    angular
        .module('hmisApp')
        .controller('BillingDialogController', BillingDialogController);

    BillingDialogController.$inject = ['$timeout','$state', '$scope', '$stateParams', 'entity', 'Billing', 'Profile', 'User', 'House', 'Floor', 'Flat', 'Rent'];

    function BillingDialogController ($timeout,$state, $scope, $stateParams, entity, Billing, Profile, User, House, Floor, Flat, Rent) {
        var vm = this;

        vm.billing = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        // vm.profiles = Profile.query();
        // vm.users = User.query();
        vm.houses = House.query();
        vm.floors = Floor.query();
        vm.flats = Flat.query();
        vm.rents = Rent.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            //$uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.billing.id !== null) {
                Billing.update(vm.billing, onSaveSuccess, onSaveError);
            } else {
                Billing.save(vm.billing, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hmisApp:billingUpdate', result);
           // $uibModalInstance.close(result);
            $state.go('billing', {}, {reload: 'billing'});

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
