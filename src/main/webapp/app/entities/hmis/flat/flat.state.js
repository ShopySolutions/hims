(function() {
    'use strict';

    angular
        .module('hmisApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('flat', {
            parent: 'hmis',
            url: '/flat',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'hmisApp.flat.home.title'
            },
            views: {
                'hmisManagementView@hmis': {
                    templateUrl: 'app/entities/hmis/flat/flats.html',
                    controller: 'FlatController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('flat');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('flat-detail', {
            parent: 'flat',
            url: '/flat/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'hmisApp.flat.detail.title'
            },
            views: {
                'hmisManagementView@hmis': {
                    templateUrl: 'app/entities/hmis/flat/flat-detail.html',
                    controller: 'FlatDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('flat');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Flat', function($stateParams, Flat) {
                    return Flat.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'flat',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('flat-detail.edit', {
            parent: 'flat-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            views: {
                'hmisManagementView@hmis': {
                    templateUrl: 'app/entities/hmis/flat/flat-dialog.html',
                    controller: 'FlatDialogController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', '$state','Flat', function($stateParams, $state,Flat) {
                    return Flat.get({id : $stateParams.id}).$promise;
                }]
            }

            /*onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/hmis/flat/flat-dialog.html',
                    controller: 'FlatDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Flat', function(Flat) {
                            return Flat.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]*/
        })
        .state('flat.new', {
            parent: 'flat',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            views: {
                'hmisManagementView@hmis': {
                    templateUrl: 'app/entities/hmis/flat/flat-dialog.html',
                    controller: 'FlatDialogController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: function () {
                    return {
                        flatNameOrNo: null,
                        flatSize: null,
                        totalBedRoom: null,
                        drawDinDraCumDin: null,
                        totalWashroom: null,
                        kitchen: null,
                        totalBelcony: null,
                        storeRoom: null,
                        floorType: null,
                        flatFair: null,
                        serviceCharge: null,
                        totalFair: null,
                        createdate: null,
                        createBy: null,
                        updateDate: null,
                        updateBy: null,
                        id: null
                    };
                }
            }
            /*onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/hmis/flat/flat-dialog.html',
                    controller: 'FlatDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                flatNameOrNo: null,
                                flatSize: null,
                                totalBedRoom: null,
                                drawDinDraCumDin: null,
                                totalWashroom: null,
                                kitchen: null,
                                totalBelcony: null,
                                storeRoom: null,
                                floorType: null,
                                flatFair: null,
                                serviceCharge: null,
                                totalFair: null,
                                createdate: null,
                                createBy: null,
                                updateDate: null,
                                updateBy: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('flat', null, { reload: 'flat' });
                }, function() {
                    $state.go('flat');
                });
            }]*/
        })
        .state('flat.edit', {
            parent: 'flat',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            views: {
                'hmisManagementView@hmis': {
                    templateUrl: 'app/entities/hmis/flat/flat-dialog.html',
                    controller: 'FlatDialogController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', '$state','Flat', function($stateParams, $state,Flat) {
                    return Flat.get({id : $stateParams.id}).$promise;
                }]
            }
            /*onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/hmis/flat/flat-dialog.html',
                    controller: 'FlatDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Flat', function(Flat) {
                            return Flat.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('flat', null, { reload: 'flat' });
                }, function() {
                    $state.go('^');
                });
            }]*/
        })
        .state('flat.delete', {
            parent: 'flat',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/hmis/flat/flat-delete-dialog.html',
                    controller: 'FlatDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Flat', function(Flat) {
                            return Flat.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('flat', null, { reload: 'flat' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
