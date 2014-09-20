var LoginCtrl = function ($scope, $http, $routeParams, $rootScope, $location) {

    $scope.mode = "register";

    $scope.goto_login = function () {
        $scope.mode = "login";
    };

    $scope.login = function () {
        $rootScope.need_login = false;
    };

    $scope.register = function () {
        $rootScope.need_login = false;
    };
};
LoginCtrl.$inject = ['$scope', '$http', '$routeParams', '$rootScope', '$location'];