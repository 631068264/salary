<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<!DOCTYPE HTML>
<html ng-app>
  <head>
  	<%@ include file="/WEB-INF/jsp/public/common.jspf" %>
    <title>欢迎页面</title>
    <script >
    	var UserCtrl = ['$scope','$http',function($scope,$http){
			$scope.topUrl="../public/top.html";
			$http.get('./getUser').success(function(data){
				$scope.user = data;
			});
		}];
		
		
    </script>
  </head>
  
  <body >
  	<div class="container" ng-controller="UserCtrl">
  	<header ng-include="topUrl" style="margin-bottom:15%"></header>
	
		<div class="hero-unit">
	      <h1>Hello,{{user.userName}}!</h1>
	      <p>欢迎使用本系统</p>
		</div>
  	</div>
   		
   		
  </body>
</html>
