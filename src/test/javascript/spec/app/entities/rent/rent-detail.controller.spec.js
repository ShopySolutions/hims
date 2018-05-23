'use strict';

describe('Controller Tests', function() {

    describe('Rent Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockRent, MockProfile, MockUser, MockHouse, MockFloor, MockFlat;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockRent = jasmine.createSpy('MockRent');
            MockProfile = jasmine.createSpy('MockProfile');
            MockUser = jasmine.createSpy('MockUser');
            MockHouse = jasmine.createSpy('MockHouse');
            MockFloor = jasmine.createSpy('MockFloor');
            MockFlat = jasmine.createSpy('MockFlat');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Rent': MockRent,
                'Profile': MockProfile,
                'User': MockUser,
                'House': MockHouse,
                'Floor': MockFloor,
                'Flat': MockFlat
            };
            createController = function() {
                $injector.get('$controller')("RentDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hmisApp:rentUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
