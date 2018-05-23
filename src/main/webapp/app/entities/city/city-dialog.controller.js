(function() {
    'use strict';

    angular
        .module('hmisApp')
        .controller('CityDialogController', CityDialogController);

    CityDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'City', 'Country', 'State','findStateByCountry'];

    function CityDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, City, Country, State,findStateByCountry) {
        var vm = this;

        vm.city = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.countries = Country.query();
        // vm.states = State.query();

        $scope.getStateByCountry=function (getId) {
            findStateByCountry.query({country: getId},function (result) {
                vm.states =result

            })
        };
        if ($stateParams.id) {

            $scope.getStateByCountry(vm.city.country.id);

        }
        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.city.id !== null) {
                City.update(vm.city, onSaveSuccess, onSaveError);
            } else {
                City.save(vm.city, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hmisApp:cityUpdate', result);
            $uibModalInstance.close(result);
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








        $(document).ready(function() {
            $('.js-example-basic-single').select2();
        });
    }
})();
