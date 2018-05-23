(function() {
    'use strict';
    angular
        .module('hmisApp')
        .factory('State', State)
        .factory('findStateByCountry', findStateByCountry);

    State.$inject = ['$resource', 'DateUtils'];
    findStateByCountry.$inject = ['$resource', 'DateUtils'];

    function State ($resource, DateUtils) {
        var resourceUrl =  'api/states/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createdate = DateUtils.convertLocalDateFromServer(data.createdate);
                        data.updatedate = DateUtils.convertLocalDateFromServer(data.updatedate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.createdate = DateUtils.convertLocalDateToServer(copy.createdate);
                    copy.updatedate = DateUtils.convertLocalDateToServer(copy.updatedate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.createdate = DateUtils.convertLocalDateToServer(copy.createdate);
                    copy.updatedate = DateUtils.convertLocalDateToServer(copy.updatedate);
                    return angular.toJson(copy);
                }
            }
        });
    }function findStateByCountry ($resource, DateUtils) {
        var resourceUrl =  'api/states/findStateByCountry/:country';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},

        });
    }
})();
