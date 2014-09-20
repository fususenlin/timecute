$routeProvider.when("/login", {
    resource: 'login/login',
    controller: 'LoginCtrl'
});

$routeProvider.when("/", {
    resource: 'first/first',
    controller: 'FirstCtrl',
    require: ["../resource/components/unslider/unslider.js"]
});
$routeProvider.when("/editor", {
    resource: 'editor/editor',
    controller: 'EditorCtrl',
    require: ["../resource/components/marked/marked.js", "../resource/components/marked/lib.js"]
});
$route("time.routes", "modules/");