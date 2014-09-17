window.$url = function (path) {
    var url = location.pathname;
    var pos = url.lastIndexOf("/");
    if (pos == -1) {
        pos = url.lastIndexOf("\\")
    }
    var filename = url.substr(0, pos)
    return filename + "/../" + path;
}

window.$directive = function (path) {
    return $url("common/directives" + path);
}

angular.module('frame.$directive', [])
    .run(function ($rootScope, $location) {
        $rootScope.$directive = function (path) {
            return window.$directive(path);
        };
    });