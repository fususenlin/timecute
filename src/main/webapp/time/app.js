g = g || {};
g._os = "pc";


angular.module('time', [
       'ngRoute',
       'ngResource',
       'ngSanitize',
       'ngTouch',
       'ngAnimate',
       'ui.bootstrap',
       'frame.$directive',

       'time.config',
       'time.filters',
       'time.run',
       'time.routes'

    ],

    function ($routeProvider) {
        $routeProvider.otherwise({
            redirectTo: '/'
        });
    }
);