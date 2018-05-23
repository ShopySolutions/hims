(function() {
    'use strict';

    angular
        .module('hmisApp')
        .controller('BillingDeleteController',BillingDeleteController);

    BillingDeleteController.$inject = ['$uibModalInstance', 'entity', 'Billing'];

    function BillingDeleteController($uibModalInstance, entity, Billing) {
        var vm = this;

        vm.billing = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Billing.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
