'use strict';

describe('Controller Tests', function() {

    describe('Flat Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockFlat, MockProfile, MockUser, MockHouse, MockFloor;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockFlat = jasmine.createSpy('MockFlat');
            MockProfile = jasmine.createSpy('MockProfile');
            MockUser = jasmine.createSpy('MockUser');
            MockHouse = jasmine.createSpy('MockHouse');
            MockFloor = jasmine.createSpy('MockFloor');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Flat': MockFlat,
                'Profile': MockProfile,
                'User': MockUser,
                'House': MockHouse,
                'Floor': MockFloor
            };
            createController = function() {
                $injector.get('$controller')("FlatDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hmisApp:flatUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
