var EditorCtrl = function ($rootScope, $scope, $modal, $http, $location) {

    $scope.mode = "edit";

    $scope.to_editor = function () {
        $location.path("/editor");
    };

    $scope.mode_to = function () {
        $scope.mode = "to";
        $scope.words = $('.textarea').wysihtml5().val();
    };

    $scope.$$postDigest(function () {
        $('.textarea').wysihtml5({
            "locale": "zh-CN",
            "font-styles": true, //Font styling, e.g. h1, h2, etc. Default true
            "emphasis": true, //Italics, bold, etc. Default true
            "lists": true, //(Un)ordered lists, e.g. Bullets, Numbers. Default true
            "html": false, //Button which allows you to edit the generated HTML. Default false
            "link": true, //Button to insert a link. Default true
            "image": true, //Button to insert an image. Default true,
            "color": false, //Button to change color of font
            "size": 'sm' //Button size like sm, xs etc.
        });
    });
};
EditorCtrl.$inject = ['$rootScope', '$scope', '$modal', '$http', '$location'];