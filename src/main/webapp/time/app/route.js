$routeProvider.when("/login", {
    resource: 'login/login',
    controller: 'LoginCtrl'
});
$routeProvider.when("/advice", {
    resource: 'advice/advice',
    controller: 'AdviceCtrl'
});
$routeProvider.when("/infos/about", {
    resource: 'infos/about/about'
});
$routeProvider.when("/infos/agreement", {
    resource: 'infos/agreement/agreement'
});
$routeProvider.when("/infos/privacy", {
    resource: 'infos/privacy/privacy'
});
$routeProvider.when("/infos/disclaimer", {
    resource: 'infos/disclaimer/disclaimer'
});
$routeProvider.when("/", {
    resource: 'first/first',
    controller: 'FirstCtrl',
    require: ["../resource/components/unslider/unslider.js"]
});
$routeProvider.when("/editor", {
    resource: 'editor/editor',
    controller: 'EditorCtrl',
    require: function () {
        var requires = [];
        requires.push("../resource/components/marked/marked.js");
        requires.push("../resource/components/marked/lib.js");
        requires.push("../resource/bootstrap/bootstrap-datepicker/css/datepicker3.css");
        requires.push("../resource/bootstrap/bootstrap-datepicker/js/bootstrap-datepicker.js");
        return requires;
    }
});
$route("time.routes", "modules/");