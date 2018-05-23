(function() {
    'use strict';

    angular
        .module('hmisApp')
        .controller('RentDeleteController',RentDeleteController);

    RentDeleteController.$inject = ['$uibModalInstance', 'entity', 'Rent'];

    function RentDeleteController($uibModalInstance, entity, Rent) {
        var vm = this;

        vm.rent = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Rent.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
