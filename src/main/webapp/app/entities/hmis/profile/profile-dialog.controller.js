(function () {
    'use strict';

    angular
        .module('hmisApp')
        .controller('ProfileDialogController', ProfileDialogController);

    ProfileDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'DataUtils', 'entity', 'Profile', 'Country', 'State', 'City', 'User', 'findStateByCountry', 'findCityByState'];

    function ProfileDialogController($timeout, $scope, $stateParams, $uibModalInstance, $q, DataUtils, entity, Profile, Country, State, City, User, findStateByCountry, findCityByState) {
        var vm = this;

        vm.profile = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.countries = Country.query();
        // vm.states = State.query();
        // vm.cities = City.query();
        // vm.users = User.query();

        $scope.getStateByCountry = function (getId) {
            console.log(getId)

            findStateByCountry.query({country: getId}, function (result) {
                vm.states = result

            })
        };

        $scope.getCityByState = function (getId) {
            console.log(getId)
            findCityByState.query({state: getId}, function (result) {
                vm.cities = result

            })
        };
        if ($stateParams.id) {
            Profile.get({id: $stateParams.id}, function (result) {
                vm.profile = result;
                $scope.getStateByCountry(vm.profile.country.id);
                $scope.getCityByState(vm.profile.state.id);
            });

        }

        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function save() {
            vm.isSaving = true;
            if (vm.profile.id !== null) {
                Profile.update(vm.profile, onSaveSuccess, onSaveError);
            } else {
                Profile.save(vm.profile, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess(result) {
            $scope.$emit('hmisApp:profileUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError() {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createdate = false;
        vm.datePickerOpenStatus.updateDate = false;

        vm.setProfileImage = function ($file, profile) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function (base64Data) {
                    $scope.$apply(function () {
                        profile.profileImage = base64Data;
                        profile.profileImageContentType = $file.type;
                    });
                });
            }
        };

        vm.setNidOrPassport = function ($file, profile) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function (base64Data) {
                    $scope.$apply(function () {
                        profile.nidOrPassport = base64Data;
                        profile.nidOrPassportContentType = $file.type;
                    });
                });
            }
        };

        function openCalendar(date) {
            vm.datePickerOpenStatus[date] = true;
        }

        $(document).ready(function () {
            $('.js-example-basic-single').select2();
        });
    }
})();
