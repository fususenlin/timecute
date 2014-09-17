angular.module('time.config', [])
    .config(function ($httpProvider) {

        $httpProvider.responseInterceptors.push(function ($q) {
            return function (promise) {
                return promise.then(function (response) {
                    if (response.data && null != response.data.error_msg) {
                        return $q.reject(response);
                    }
                    return response;
                });
            };
        });
        $httpProvider.defaults.transformResponse.push(function (data, header, status) {
            if (data.error_msg) {
                bootbox.error(data.error_msg);
                return data;
            }
            if (data.detail) {
                bootbox.error(data.detail);
                return data;
            }
            return data;
        });
        $httpProvider.defaults.transformRequest = function (data, headers) {
            /**
             * The workhorse; converts an object to x-www-form-urlencoded serialization.
             * @param {Object} obj
             * @return {String}
             */
            return angular.isObject(data) && String(data) !== '[object File]' ? param(data) : data;
        };
        $httpProvider.defaults.headers.put['Content-Type'] = 'application/x-www-form-urlencoded; charset=UTF-8';
        $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded; charset=UTF-8';

    });