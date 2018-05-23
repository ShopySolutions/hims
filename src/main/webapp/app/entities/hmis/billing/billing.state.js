(function () {
    'use strict';

    angular
        .module('hmisApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('billing', {
                parent: 'hmis',
                url: '/billing',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'hmisApp.billing.home.title'
                },
                views: {
                    'hmisManagementView@hmis': {
                        templateUrl: 'app/entities/hmis/billing/billings.html',
                        controller: 'BillingController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('billing');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('billing-detail', {
                parent: 'billing',
                url: '/billing/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'hmisApp.billing.detail.title'
                },
                views: {
                    'hmisManagementView@hmis': {
                        templateUrl: 'app/entities/hmis/billing/billing-detail.html',
                        controller: 'BillingDetailController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('billing');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Billing', function ($stateParams, Billing) {
                        return Billing.get({id: $stateParams.id}).$promise;
                    }],
                    previousState: ["$state", function ($state) {
                        var currentStateData = {
                            name: $state.current.name || 'billing',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
            })
            .state('billing-detail.edit', {
                parent: 'billing-detail',
                url: '/detail/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'hmisManagementView@hmis': {
                        templateUrl: 'app/entities/hmis/billing/billing-dialog.html',
                        controller: 'BillingDialogController',
                        controllerAs: 'vm'
                    }
                },

                resolve: {
                    entity: ['$stateParams', '$state', 'Billing', function ($stateParams, $state, Billing) {
                        return Billing.get({id: $stateParams.id}).$promise;
                    }]
                }


                /* onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                     $uibModal.open({
                         templateUrl: 'app/entities/hmis/billing/billing-dialog.html',
                         controller: 'BillingDialogController',
                         controllerAs: 'vm',
                         backdrop: 'static',
                         size: 'lg',
                         resolve: {
                             entity: ['Billing', function(Billing) {
                                 return Billing.get({id : $stateParams.id}).$promise;
                             }]
                         }
                     }).result.then(function() {
                         $state.go('^', {}, { reload: false });
                     }, function() {
                         $state.go('^');
                     });
                 }]*/
            })
            .state('billing.new', {
                parent: 'billing',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'hmisManagementView@hmis': {
                        templateUrl: 'app/entities/hmis/billing/billing-dialog.html',
                        controller: 'BillingDialogController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    entity: function () {
                        return {
                            flatFair: null,
                            electricityBill: null,
                            gasBill: null,
                            internetBill: null,
                            transactionId: null,
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
                        templateUrl: 'app/entities/hmis/billing/billing-dialog.html',
                        controller: 'BillingDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    flatFair: null,
                                    electricityBill: null,
                                    gasBill: null,
                                    internetBill: null,
                                    transactionId: null,
                                    createdate: null,
                                    createBy: null,
                                    updateDate: null,
                                    updateBy: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function() {
                        $state.go('billing', null, { reload: 'billing' });
                    }, function() {
                        $state.go('billing');
                    });
                }]*/
            })
            .state('billing.edit', {
                parent: 'billing',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },

                views: {
                    'hmisManagementView@hmis': {
                        templateUrl: 'app/entities/hmis/billing/billing-dialog.html',
                        controller: 'BillingDialogController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    entity: ['$stateParams', '$state','Billing', function ($stateParams, $state,Billing) {
                        return Billing.get({id: $stateParams.id}).$promise;
                    }]
                }

               /* onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/hmis/billing/billing-dialog.html',
                        controller: 'BillingDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['Billing', function (Billing) {
                                return Billing.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('billing', null, {reload: 'billing'});
                    }, function () {
                        $state.go('^');
                    });
                }]*/
            })
            .state('billing.delete', {
                parent: 'billing',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/hmis/billing/billing-delete-dialog.html',
                        controller: 'BillingDeleteController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            entity: ['Billing', function (Billing) {
                                return Billing.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('billing', null, {reload: 'billing'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            });
    }

})();
