var EditorCtrl = function ($rootScope, $scope, $modal, $http, $location) {

    $scope.mode = "edit";

    $scope.edit_content = "#使用markdown语法";
    $scope.letter = {
        name: "李茂盛",
        words: " ",
        email: "limaoshengcpp@163.com"
    };

    $scope.$watch('edit_content', function (data) {
        $scope.letter.words = window.marked($scope.edit_content);
    });
    $scope.to_editor = function () {
        $location.path("/editor");
    };

    $scope.to_edit = function () {
        $scope.mode = "edit";
    };
    $scope.mode_to = function () {
        $scope.mode = "send";
    };

    $scope.send = function () {
        bootbox.message("发送成功");
    };

    $scope.$$postDigest(function () {

    });
};
EditorCtrl.$inject = ['$rootScope', '$scope', '$modal', '$http', '$location'];