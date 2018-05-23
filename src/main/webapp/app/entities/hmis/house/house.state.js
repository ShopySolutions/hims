(function () {
    'use strict';

    angular
        .module('hmisApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('house', {
                parent: 'hmis',
                url: '/house',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'hmisApp.house.home.title'
                },
                views: {
                    'hmisManagementView@hmis': {
                        templateUrl: 'app/entities/hmis/house/houses.html',
                        controller: 'HouseController',
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
            .state('house-detail', {
                parent: 'house',
                url: '/house/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'hmisApp.house.detail.title'
                },
                views: {
                    'hmisManagementView@hmis': {
                        templateUrl: 'app/entities/hmis/house/house-detail.html',
                        controller: 'HouseDetailController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('house');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'House', function ($stateParams, House) {
                        return House.get({id: $stateParams.id}).$promise;
                    }],
                    previousState: ["$state", function ($state) {
                        var currentStateData = {
                            name: $state.current.name || 'house',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
            })
            .state('house-detail.edit', {
                parent: 'house-detail',
                url: '/detail/edit',
                data: {
                    authorities: ['ROLE_USER']
                },

                views: {
                    'hmisManagementView@hmis': {
                        templateUrl: 'app/entities/hmis/house/house-dialog.html',
                        controller: 'HouseDialogController',
                        controllerAs: 'vm'
                    }
                },

                resolve: {
                    entity: ['$stateParams', '$state', 'House', function ($stateParams, $state, House) {
                        return House.get({id: $stateParams.id}).$promise;
                    }]
                }
                /*onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/hmis/house/house-dialog.html',
                        controller: 'HouseDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['House', function(House) {
                                return House.get({id : $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function() {
                        $state.go('^', {}, { reload: false });
                    }, function() {
                        $state.go('^');
                    });
                }]*/
            })
            .state('house.new', {
                parent: 'house',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER']
                },


                views: {
                    'hmisManagementView@hmis': {
                        templateUrl: 'app/entities/hmis/house/house-dialog.html',
                        controller: 'HouseDialogController',
                        controllerAs: 'vm'
                    }
                },

                resolve: {
                    entity: function () {
                        return {
                            houseName: null,
                            houseNo: null,
                            address: null,
                            houseToFloorNo: null,
                            ownToFloorNo: null,
                            lat: null,
                            lon: null,
                            createdate: null,
                            createBy: null,
                            updateDate: null,
                            updateBy: null,
                            image: null,
                            imageContentType: null,
                            id: null
                        };
                    }
                }


                /*onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/hmis/house/house-dialog.html',
                        controller: 'HouseDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',

                    }).result.then(function() {
                        $state.go('house', null, { reload: 'house' });
                    }, function() {
                        $state.go('house');
                    });
                }]*/
            })
            .state('house.edit', {
                parent: 'house',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER']
                },

                views: {
                    'hmisManagementView@hmis': {
                        templateUrl: 'app/entities/hmis/house/house-dialog.html',
                        controller: 'HouseDialogController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    entity: ['$stateParams', '$state', 'House', function ($stateParams, $state, House) {
                        return House.get({id: $stateParams.id}).$promise;
                    }]
                }

                /*
                 onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                     $uibModal.open({
                         templateUrl: 'app/entities/hmis/house/house-dialog.html',
                         controller: 'HouseDialogController',
                         controllerAs: 'vm',
                         backdrop: 'static',
                         size: 'lg',
                         resolve: {
                             entity: ['House', function(House) {
                                 return House.get({id : $stateParams.id}).$promise;
                             }]
                         }
                     }).result.then(function() {
                         $state.go('house', null, { reload: 'house' });
                     }, function() {
                         $state.go('^');
                     });
                 }]*/
            })


            .state('house.delete', {
                parent: 'house',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/hmis/house/house-delete-dialog.html',
                        controller: 'HouseDeleteController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            entity: ['House', function (House) {
                                return House.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('house', null, {reload: 'house'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            });
    }

})();
