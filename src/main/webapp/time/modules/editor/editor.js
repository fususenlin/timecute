var EditorCtrl = function ($rootScope, $scope, $modal, $http, $location) {

    $scope.mode = "send";

    $scope.letter = {};

    $scope.edit_content = "###随便写点";

    $scope.letter.users = [];
    $scope.letter.users.push({
        name: "李茂盛",
        email: "limaoshengcpp@163.com"
    });

    $scope.letter.from = {
        name: "苏轼",
        email: "sushi@tang.com"
    };
    $scope.add = function () {
        $scope.letter.users.push({
            name: Random.name(),
            email: Random.email()
        });
    };
    $scope.minus = function (index) {
        bootbox.confirm("确定要删除吗?", function (ret) {
            if (ret) {
                $scope.letter.users.splice(index, 1);
                apply($scope);
            }
        });

    };
    $scope.$watch('edit_content', function (data) {
        $scope.letter.words = window.marked($scope.edit_content);
        var div = document.getElementById('preview');
        if (div) {
            div.scrollTop = div.scrollHeight;
        }
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