(function() {
    'use strict';

    angular
        .module('hmisApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('floor', {
            parent: 'hmis',
            url: '/floor',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'hmisApp.floor.home.title'
            },
            views: {
                'hmisManagementView@hmis': {
                    templateUrl: 'app/entities/hmis/floor/floors.html',
                    controller: 'FloorController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('floor');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('floor-detail', {
            parent: 'floor',
            url: '/floor/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'hmisApp.floor.detail.title'
            },
            views: {
                'hmisManagementView@hmis': {
                    templateUrl: 'app/entities/hmis/floor/floor-detail.html',
                    controller: 'FloorDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('floor');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Floor', function($stateParams, Floor) {
                    return Floor.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'floor',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('floor-detail.edit', {
            parent: 'floor-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },

            views: {
                'hmisManagementView@hmis': {
                    templateUrl: 'app/entities/hmis/floor/floor-dialog.html',
                    controller: 'FloorDialogController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', '$state','Floor', function($stateParams, $state,Floor) {
                    return Floor.get({id : $stateParams.id}).$promise;
                }]
            }

            /*onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/hmis/floor/floor-dialog.html',
                    controller: 'FloorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Floor', function(Floor) {
                            return Floor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]*/
        })
        .state('floor.new', {
            parent: 'floor',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            views: {
                'hmisManagementView@hmis': {
                    templateUrl: 'app/entities/hmis/floor/floor-dialog.html',
                    controller: 'FloorDialogController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: function () {
                    return {
                        floorNo: null,
                        totalFlat: null,
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
                    templateUrl: 'app/entities/hmis/floor/floor-dialog.html',
                    controller: 'FloorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                floorNo: null,
                                totalFlat: null,
                                createdate: null,
                                createBy: null,
                                updateDate: null,
                                updateBy: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('floor', null, { reload: 'floor' });
                }, function() {
                    $state.go('floor');
                });
            }]*/
        })
        .state('floor.edit', {
            parent: 'floor',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            views: {
                'hmisManagementView@hmis': {
                    templateUrl: 'app/entities/hmis/floor/floor-dialog.html',
                    controller: 'FloorDialogController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', '$state','Floor', function($stateParams, $state,Floor) {
                    return Floor.get({id : $stateParams.id}).$promise;
                }]
            }
            /*onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/hmis/floor/floor-dialog.html',
                    controller: 'FloorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Floor', function(Floor) {
                            return Floor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('floor', null, { reload: 'floor' });
                }, function() {
                    $state.go('^');
                });
            }]*/
        })
        .state('floor.delete', {
            parent: 'floor',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/hmis/floor/floor-delete-dialog.html',
                    controller: 'FloorDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Floor', function(Floor) {
                            return Floor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('floor', null, { reload: 'floor' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
