'use strict'

var apply = function (scope, fn) {
    fn && fn();
    scope.$$phase || scope.$apply();
};

var currentRoot = "app";

var loadJs = function () {
    var pathList = arguments;
    return {
        delay: ['$q',
            function ($q) {
                var delay = $q.defer();
                var resolve = function () {
                    length--;
                    if (length == 0) {
                        delay.resolve();
                        var init = function () {
                            document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
                                WeixinJSBridge.call('hideToolbar');
                            });
                        }();
                    }
                };
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
                        resolve();
                    });
                }
                return delay.promise;
        }]
    };
};


var param = function (obj) {
    var query = '';
    var name, value, fullSubName, subName, subValue, innerObj, i;

    for (name in obj) {
        value = obj[name];

        if (value instanceof Array) {
            for (i = 0; i < value.length; ++i) {
                subValue = value[i];
                fullSubName = name + '[' + i + ']';
                innerObj = {};
                innerObj[fullSubName] = subValue;
                query += param(innerObj) + '&';
            }
        } else if (value instanceof Object) {
            for (subName in value) {
                subValue = value[subName];
                fullSubName = name + '.' + subName + '';
                innerObj = {};
                innerObj[fullSubName] = subValue;
                query += param(innerObj) + '&';
            }
        } else if (value !== undefined && value !== null) {
            query += encodeURIComponent(name) + '=' + encodeURIComponent(value) + '&';
        }
    }

    return query.length ? query.substr(0, query.length - 1) : query;
};

function isyesterday(today, messageDate) {
    var yesterday_milliseconds = today.getTime() - 1000 * 60 * 60 * 24;
    var yesterday = new Date();
    yesterday.setTime(yesterday_milliseconds);

    var strYear = yesterday.getFullYear();
    var strDay = yesterday.getDate();
    var strMonth = yesterday.getMonth();

    if (strYear !== messageDate.getFullYear()) {
        return false;
    }
    if (strDay !== messageDate.getDate()) {
        return false;
    }
    if (strMonth !== messageDate.getMonth()) {
        return false;
    }
    return true;
}

function istoday(today, messageDate) {
    if (today.getFullYear() !== messageDate.getFullYear()) {
        return false;
    }
    if (today.getDate() !== messageDate.getDate()) {
        return false;
    }
    if (today.getMonth() !== messageDate.getMonth()) {
        return false;
    }
    return true;
}

var system = {
    clientWidth: document.body.clientWidth,
    clientHeight: document.body.clientHeight,
    os: navigator.userAgent.split(";")[0].split('(')[1]
}

if($.scojs_message && bootbox) {
    bootbox.message = function(msg) {
        $.scojs_message(msg, $.scojs_message.TYPE_OK);
    },
    bootbox.error = function(msg) {
        $.scojs_message(msg, $.scojs_message.TYPE_ERROR);
    }
}