(function() {
    'use strict';

    angular
        .module('hmisApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('profile', {
            parent: 'hmis',
            url: '/profile',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'hmisApp.profile.home.title'
            },
            views: {
                'hmisManagementView@hmis': {
                    templateUrl: 'app/entities/hmis/profile/profiles.html',
                    controller: 'ProfileController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('profile');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('profile-detail', {
            parent: 'profile',
            url: '/profile/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'hmisApp.profile.detail.title'
            },
            views: {
                'hmisManagementView@hmis': {
                    templateUrl: 'app/entities/hmis/profile/profile-detail.html',
                    controller: 'ProfileDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('profile');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Profile', function($stateParams, Profile) {
                    //return Profile.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'profile',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('profile-detail.edit', {
            parent: 'profile-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/hmis/profile/profile-dialog.html',
                    controller: 'ProfileDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Profile', function(Profile) {
                            //return Profile.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('profile.new', {
            parent: 'profile',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/hmis/profile/profile-dialog.html',
                    controller: 'ProfileDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                firstName: null,
                                lastName: null,
                                mobileNo: null,
                                telephoneNo: null,
                                email: null,
                                nidOrPassportNo: null,
                                address: null,
                                gender: null,
                                religion: null,
                                createdate: null,
                                createBy: null,
                                updateDate: null,
                                updateBy: null,
                                profileImage: null,
                                profileImageContentType: null,
                                nidOrPassport: null,
                                nidOrPassportContentType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('profile', null, { reload: 'profile' });
                }, function() {
                    $state.go('profile');
                });
            }]
        })
        .state('profile.edit', {
            parent: 'profile',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/hmis/profile/profile-dialog.html',
                    controller: 'ProfileDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Profile', function(Profile) {
                            return Profile.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('profile', null, { reload: 'profile' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('profile.delete', {
            parent: 'profile',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/hmis/profile/profile-delete-dialog.html',
                    controller: 'ProfileDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Profile', function(Profile) {
                            return Profile.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('profile', null, { reload: 'profile' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
