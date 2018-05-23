(function() {
    'use strict';

    angular
        .module('hmisApp')
        .controller('HouseDialogController', HouseDialogController);

    HouseDialogController.$inject = ['$timeout','$state', '$scope', '$stateParams', 'DataUtils', 'entity', 'House', 'Country', 'State', 'City', 'Profile', 'User'];

    function HouseDialogController ($timeout,$state, $scope, $stateParams, DataUtils, entity, House, Country, State, City, Profile, User) {
        var vm = this;

        vm.house = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.countries = Country.query();
        vm.states = State.query();
        vm.cities = City.query();
        // vm.profiles = Profile.query();
        // vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
           // $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.house.id !== null) {
                House.update(vm.house, onSaveSuccess, onSaveError);
            } else {
                House.save(vm.house, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hmisApp:houseUpdate', result);
            //$uibModalInstance.close(result);
            $state.go('house', {}, { reload: 'house' });

            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createdate = false;
        vm.datePickerOpenStatus.updateDate = false;

        vm.setImage = function ($file, house) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        house.image = base64Data;
                        house.imageContentType = $file.type;
                    });
                });
            }
        };

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
