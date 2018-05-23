'use strict';

describe('Controller Tests', function() {

    describe('Billing Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockBilling, MockProfile, MockUser, MockHouse, MockFloor, MockFlat, MockRent;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockBilling = jasmine.createSpy('MockBilling');
            MockProfile = jasmine.createSpy('MockProfile');
            MockUser = jasmine.createSpy('MockUser');
            MockHouse = jasmine.createSpy('MockHouse');
            MockFloor = jasmine.createSpy('MockFloor');
            MockFlat = jasmine.createSpy('MockFlat');
            MockRent = jasmine.createSpy('MockRent');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Billing': MockBilling,
                'Profile': MockProfile,
                'User': MockUser,
                'House': MockHouse,
                'Floor': MockFloor,
                'Flat': MockFlat,
                'Rent': MockRent
            };
            createController = function() {
                $injector.get('$controller')("BillingDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hmisApp:billingUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
