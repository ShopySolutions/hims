(function() {
    'use strict';

    angular
        .module('hmisApp')
        .controller('hmisHomeController', FloorController);

    FloorController.$inject = ['Floor', 'ParseLinks', 'AlertService', 'paginationConstants'];

    function FloorController(Floor, ParseLinks, AlertService, paginationConstants) {


    }
})();
