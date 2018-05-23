(function() {
    'use strict';

    angular
        .module('hmisApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('hmis', {
            parent: 'entity',
            url: '/hmis',
            data: {
                authorities: [],
                pageTitle: 'hmisApp.house.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/hmis/hmis-info.html',
                    controller: 'hmisHomeController',
                    controllerAs: 'vm'
                },
                'hmisManagementView@hmis':{
                    templateUrl: 'app/entities/hmis/hmis-dashboard.html',
                    controller: 'hmisHomeController',
                    controllerAs: 'vm'

                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('house');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })



    }

})();
