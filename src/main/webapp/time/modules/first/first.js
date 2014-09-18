var FirstCtrl = function ($rootScope, $scope, $modal, $http, $location) {

    $scope.mode = "register";

    $scope.to_editor = function () {
        $location.path("/editor")
    };

    $scope.$$postDigest(function () {
        $('.banner').unslider({
            speed: 500, //  The speed to animate each slide (in milliseconds)
            delay: 3000, //  The delay between slide animations (in milliseconds)
            complete: function () {}, //  A function that gets called after every slide animation
            keys: true, //  Enable keyboard (left, right) arrow shortcuts
            dots: true, //  Display dot navigation
            fluid: false //  Support responsive design. May break non-responsive designs
        });
    });
};
FirstCtrl.$inject = ['$rootScope', '$scope', '$modal', '$http', '$location'];