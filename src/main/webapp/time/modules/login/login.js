var LoginCtrl = function ($scope, $http, $timeout, $rootScope,$location) {
    $scope.user = {
        username: "",
        password: ""
    };

    $rootScope.need_login = "login";
    localStorage.setItem("need_login","login");

    $scope.login = function () {
        $http.post("/auth/login/", $scope.user)
            .success(function(data) {
                console.log(data);
                $rootScope.need_login = "admin";
                localStorage.setItem("need_login","admin");
                refreshCookie();
                $timeout(function(){
                    $location.path("/articles");
                },1500);

            });
    };
};
LoginCtrl.$inject = ['$scope', '$http', '$timeout', '$rootScope','$location'];