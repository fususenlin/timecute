var FirstCtrl = function ($rootScope, $scope, $modal, $http, $location) {

    $scope.to_editor = function () {
        $location.path("/editor")
    };

    $scope.letters = [];
    $scope.letters.push({
        img: "../asserts/img/time/2.jpg",
        title: "最美情书",
        small: "史上最美的情书",
        preview: "一封来自天堂的情书，写给那些年最爱的女孩，揭秘那些年最想说的话"
    });
    $scope.letters.push({
        img: "../asserts/img/time/1.jpg",
        title: "未来的我",
        small: "预言成真，不服来辩",
        preview: " 预言十年后，我会成为你<br>嚣张，可爱，有点二"
    });
    $scope.letters.push({
        img: "../asserts/img/time/3.jpg",
        title: "最好的回忆",
        small: "过往的今天",
        preview: "理想不死，奋斗不止去实践"
    });
    $scope.to_view = function () {
        location.href = "../letter.html?id=123";
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