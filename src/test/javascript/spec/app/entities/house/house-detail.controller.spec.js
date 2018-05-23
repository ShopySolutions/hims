'use strict';

describe('Controller Tests', function() {

    describe('House Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockHouse, MockCountry, MockState, MockCity, MockProfile, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockHouse = jasmine.createSpy('MockHouse');
            MockCountry = jasmine.createSpy('MockCountry');
            MockState = jasmine.createSpy('MockState');
            MockCity = jasmine.createSpy('MockCity');
            MockProfile = jasmine.createSpy('MockProfile');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'House': MockHouse,
                'Country': MockCountry,
                'State': MockState,
                'City': MockCity,
                'Profile': MockProfile,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("HouseDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hmisApp:houseUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
