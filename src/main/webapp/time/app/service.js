/**
 * 
 */
angular.module('time.services', [], function($provide) {
	$provide.factory('UserService', function() {
		return {
			userInfo:function(success){
				var data = {
					action:"userInfo"
				};
				Ajax("get","../../user",data,function(d) {
					success(d);
				});
			},
			login : function (username,password,remember,success){
				var data = {
						action:"login",
						username:username,
						password:password,
						remember:remember     //"true" or "false" 字符串
						
					};
				Ajax("post","../../user",data,function(d) {
					success(d);
				});
			},
			register : function(username,password,success){
				var data = {
						action:"register",
						username:username,
						password:password,
					};
				Ajax("post","../../user",data,function(d) {
					success(d);
				});
			},
			updatePassword : function(password,success){
				var data = {
						action:"update_pwd",
						password:password,
					};
				Ajax("post","../../user",data,function(d) {
					success(d);
				});
			},
			logout : function(password,success){
				var data = {
						action:"logout"
					};
				Ajax("post","../../user",data,function(d) {
					success(d);
				});
			},
		};
  });
});