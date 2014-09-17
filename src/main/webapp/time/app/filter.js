angular.module('time.filters', [])
    .filter('date', function () {
        return function (input) {
            if (!input) {
                return input;
            }
            var messageDate = new Date(parseInt(input) * 1000);
            var fullyear = messageDate.getFullYear(); //获取完整的年份(4位,1970-????)
            var month = messageDate.getMonth(); //获取当前月份(0-11,0代表1月)
            var date = messageDate.getDate(); //获取当前日(1-31)
            var hours = messageDate.getHours(); //获取当前小时数(0-23)
            var minutes = messageDate.getMinutes(); //获取当前分钟数(0-59)

            var today = new Date();
            return (month + 1) + "月" + date + "日 ";
        };
    });