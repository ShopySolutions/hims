(function() {
    'use strict';
    angular
        .module('hmisApp')
        .factory('Country', Country);

    Country.$inject = ['$resource', 'DateUtils'];

    function Country ($resource, DateUtils) {
        var resourceUrl =  'api/countries/:id';

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
    }
})();
