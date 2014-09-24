var EditorCtrl = function ($rootScope, $scope, $modal, $http, $location) {

    $scope.mode = "edit";

    $scope.edit_content = "#使用markdown语法";

    $scope.letter = {};

    $scope.users = [];
    $scope.users.push({
        name: "李茂盛",
        email: "limaoshengcpp@163.com"
    });
    $scope.add = function () {
        $scope.users.push({
            name: "",
            email: ""
        });
    };
    $scope.minus = function (index) {
        $scope.users.splice(index, 1);
    };
    $scope.$watch('edit_content', function (data) {
        $scope.letter.words = window.marked($scope.edit_content);
        var div = document.getElementById('preview');
        div.scrollTop = div.scrollHeight;
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

    $scope.letter.sendTime = "";
    $scope.$$postDigest(function () {
        $('#send_time').datepicker({
            format: 'yyyy-mm-dd',
            language: "zh-CN",
            startDate: '+2d',
            orientation: "top left"
        }).on("changeDate", function (e) {
            $scope.letter.sendTime = e.format("yyyy-mm-dd");
            apply($scope);
        });
    });
};
EditorCtrl.$inject = ['$rootScope', '$scope', '$modal', '$http', '$location'];