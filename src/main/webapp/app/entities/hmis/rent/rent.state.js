(function() {
    'use strict';

    angular
        .module('hmisApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('rent', {
            parent: 'hmis',
            url: '/rent',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'hmisApp.rent.home.title'
            },
            views: {
                'hmisManagementView@hmis': {
                    templateUrl: 'app/entities/hmis/rent/rents.html',
                    controller: 'RentController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('rent');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('rent-detail', {
            parent: 'rent',
            url: '/rent/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'hmisApp.rent.detail.title'
            },
            views: {
                'hmisManagementView@hmis': {
                    templateUrl: 'app/entities/hmis/rent/rent-detail.html',
                    controller: 'RentDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('rent');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Rent', function($stateParams, Rent) {
                    return Rent.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'rent',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('rent-detail.edit', {
            parent: 'rent-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            views: {
                'hmisManagementView@hmis': {
                    templateUrl: 'app/entities/hmis/rent/rent-dialog.html',
                    controller: 'RentDialogController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', '$state','Rent', function($stateParams, $state,Rent) {
                    return Rent.get({id : $stateParams.id}).$promise;
                }]
            }
            /*onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/hmis/rent/rent-dialog.html',
                    controller: 'RentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Rent', function(Rent) {
                            return Rent.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]*/
        })
        .state('rent.new', {
            parent: 'rent',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            views: {
                'hmisManagementView@hmis': {
                    templateUrl: 'app/entities/hmis/rent/rent-dialog.html',
                    controller: 'RentDialogController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: function () {
                    return {
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
                    templateUrl: 'app/entities/hmis/rent/rent-dialog.html',
                    controller: 'RentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                createdate: null,
                                createBy: null,
                                updateDate: null,
                                updateBy: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('rent', null, { reload: 'rent' });
                }, function() {
                    $state.go('rent');
                });
            }]*/
        })
        .state('rent.edit', {
            parent: 'rent',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            views: {
                'hmisManagementView@hmis': {
                    templateUrl: 'app/entities/hmis/rent/rent-dialog.html',
                    controller: 'RentDialogController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', '$state','Rent', function($stateParams, $state,Rent) {
                    return Rent.get({id : $stateParams.id}).$promise;
                }]
            }
            /*onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/hmis/rent/rent-dialog.html',
                    controller: 'RentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Rent', function(Rent) {
                            return Rent.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('rent', null, { reload: 'rent' });
                }, function() {
                    $state.go('^');
                });
            }]*/
        })
        .state('rent.delete', {
            parent: 'rent',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/hmis/rent/rent-delete-dialog.html',
                    controller: 'RentDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Rent', function(Rent) {
                            return Rent.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('rent', null, { reload: 'rent' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
