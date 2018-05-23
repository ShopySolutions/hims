'use strict';

describe('Controller Tests', function() {

    describe('Profile Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockProfile, MockCountry, MockState, MockCity, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockProfile = jasmine.createSpy('MockProfile');
            MockCountry = jasmine.createSpy('MockCountry');
            MockState = jasmine.createSpy('MockState');
            MockCity = jasmine.createSpy('MockCity');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Profile': MockProfile,
                'Country': MockCountry,
                'State': MockState,
                'City': MockCity,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("ProfileDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hmisApp:profileUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
