var HeaderCtrl = function ($scope, $routeParams, $rootScope, $location, $http) {
    $scope.$$postDigest(function () {
        setTimeout(function () {
            $(".notification-dropdown").each(function (index, el) {
                var $el = $(el);
                var $dialog = $el.find(".pop-dialog");
                var $trigger = $el.find(".trigger");

                $dialog.click(function (e) {
                    e.stopPropagation()
                });
                $dialog.find(".close-icon").click(function (e) {
                    e.preventDefault();
                    $dialog.removeClass("is-visible");
                    $trigger.removeClass("active");
                });
                $("body").click(function () {
                    $dialog.removeClass("is-visible");
                    $trigger.removeClass("active");
                });

                $trigger.click(function (e) {
                    e.preventDefault();
                    e.stopPropagation();

                    // hide all other pop-dialogs
                    $(".notification-dropdown .pop-dialog").removeClass("is-visible");
                    $(".notification-dropdown .trigger").removeClass("active")

                    $dialog.toggleClass("is-visible");
                    if ($dialog.hasClass("is-visible")) {
                        $(this).addClass("active");
                    } else {
                        $(this).removeClass("active");
                    }
                });
            });

        }, 500);
    });

    // navbar notification popups

    $scope.logout = function () {
        $http.post("/auth/logout/", $scope.user)
            .success(function(data) {
                console.log(data);
                $location.path("/login");
            });

    };
};

HeaderCtrl.$inject = ['$scope', '$routeParams', '$rootScope', '$location', '$http'];