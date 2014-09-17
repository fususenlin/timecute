$routeProvider.when("/login", {
    resource: 'login/login',
    controller: 'LoginCtrl'
});

$routeProvider.when("/", {
    resource: 'first/first',
    controller: 'FirstCtrl'
});

$route("time.routes", "modules/");