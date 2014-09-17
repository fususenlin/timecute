var g = g || {};

g._os = "client";
g._routes = [];

g.is_pc = function () {
    var system = {
        win: false,
        mac: false,
        xll: false
    };
    //检测平台
    var p = navigator.platform;
    system.win = p.indexOf("Win") == 0;
    system.mac = p.indexOf("Mac") == 0;
    system.x11 = (p == "X11") || (p.indexOf("Linux") == 0);
    //跳转语句
    if (system.win || system.mac || system.xll) { //转向电脑端
        return true; //是电脑
    } else {
        return false; //是手机
    }
};

/** 
 * ?os=pc will define os 'pc'
 * another value is 'client'
 * default is client
 */
(function () {
    if (g.is_pc()) {
        g._os = "pc";
    } else {
        g._os = "client";
    }
    var param_str = location.hash.split("?")[1];
    if (param_str) {
        var params = param_str.split("&");
        angular.forEach(params, function (param) {
            var temp = param.split("=");
            if ("_os" === temp[0]) {
                g._os = temp[1];
            }
        });
    }

}).call();

$load = function (pathList) {
    return {
        delay: ['$q',
            function ($q) {
                var delay = $q.defer();
                var length = pathList.length;
                for (var i = 0; i < pathList.length; ++i) {
                    var type = "js";
                    if (pathList[i].search(".js") != -1) {
                        type = "js";
                    } else if (pathList[i].search(".css") != -1) {
                        type = "css";
                    }
                    In.add('mod' + i, {
                        path: pathList[i],
                        type: type,
                        charset: 'utf-8'
                    });
                    In('mod' + i, function () {
                        length--;
                        if (length == 0) {
                            delay.resolve();
                        }
                    });
                }
                return delay.promise;
        }]
    };
};

$routeProvider = {
    when: function (url, route) {
        route.url = url;
        g._routes.push(route);
    }
};

$route = function (module_name, base) {

    var module = angular.module(module_name, ['ngRoute'], ['$routeProvider',
        function ($routeProvider) {

            angular.forEach(g._routes, function (route) {
                var header = base + route.resource + ".";

                if (route.controller) {
                    route.js = header + "js";
                }

                route.templateUrl = header + "html";

                if (route.differ) {
                    if (route.differ.search("html") != -1) {
                        route.templateUrl = header + g._os + ".html";
                    }
                    if (route.differ.search("js") != -1) {
                        if (route.controller) {
                            route.js = header + g._os + ".js";
                        }

                    }
                }
                route.require = route.require || [];
                route.loader = [];
                if (route.js) {
                    route.loader.push(route.js);
                }
                angular.forEach(route.require, function (require) {
                    route.loader.push(require);
                });
                if (route.controller) {
                    $routeProvider.when(route.url, {
                        templateUrl: route.templateUrl,
                        controller: route.controller,
                        resolve: $load(route.loader)
                    });
                } else {
                    if (route.loader.length > 0) {
                        $routeProvider.when(route.url, {
                            templateUrl: route.templateUrl,
                            resolve: $load(route.loader)
                        });
                    } else {
                        $routeProvider.when(route.url, {
                            templateUrl: route.templateUrl
                        });
                    }
                }

            });

        }]);
    return module;
};