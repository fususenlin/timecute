angular.module('time.run', [])
    .run(function ($rootScope, $location) {

        $rootScope.title = "平台管理";

        $rootScope.goTo = function (url) {
            $location.url(url);
        };

        $rootScope.replaceTo = function (url) {
            window.location.replace(url);
        };

        $rootScope.redirectTo = function (url) {
            window.location.href = url;
        };

        $rootScope.goback = function (step) {
            if (!step) {
                step = 1;
            }
            setTimeout(function () {
                var s = step * -1;
                history.go(s);
            }, 100);
        };
    });