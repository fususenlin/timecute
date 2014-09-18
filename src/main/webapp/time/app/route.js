$routeProvider.when("/login", {
    resource: 'login/login',
    controller: 'LoginCtrl'
});

$routeProvider.when("/", {
    resource: 'first/first',
    controller: 'FirstCtrl'
});
$routeProvider.when("/editor", {
    resource: 'editor/editor',
    controller: 'EditorCtrl',
    require: function () {
        var requires = [];
        var base = "../resource/components/wysihtml5/";
        requires.push(base + "lib/css/bootstrap3-wysiwyg5.css");
        requires.push(base + "lib/css/bootstrap3-wysiwyg5-color.css");
        requires.push(base + "lib/js/wysihtml5-0.3.0.js");
        requires.push(base + "src/bootstrap3-wysihtml5.js");
        requires.push(base + "src/locales/bootstrap-wysihtml5.zh-CN.js");
        return requires;
    }
});
$route("time.routes", "modules/");