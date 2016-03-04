<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 
<!DOCTYPE HTML>
<html ng-app>
  <head>
  <%@ include file="/WEB-INF/jsp/public/common.jspf" %>
    <title>修改个人信息</title>
	<script>
		var formCtrl = ['$scope','$http',function($scope,$http){
			$scope.topUrl="../public/top.html";
			$http.get('./getUser').success(function(data){
				$scope.user = data.user;
				$scope.department = data.department;
			});

		}];
	</script>
	<style>
	 	.error{color:red}
	 </style>
  </head>
  
  <body >
  <div class="container" ng-controller="formCtrl">
		<header ng-include="topUrl" style="margin-bottom:10%"></header>
   		<form name="form" action="${pageContext.request.contextPath}/userManage/edit" method="post" class="form-horizontal"  novalidate>
   			<input type="hidden" name="uid" ng-value="user.id" >
				<legend>个人信息</legend>
		    <div class="control-group">
				<label class="control-label">员工编号:</label>
				<div class="controls">
					<input type="text" name="userID"  required  ng-trim="true" ng-value="user.userID" ng-model="user.userID">
					<p class="help-block error" ng-show="form.userID.$error.required && form.userID.$dirty">员工编号不为空</p>
				</div>
			</div>	
			
		    <div class="control-group">
				<label class="control-label">员工姓名:</label>
				<div class="controls">
					<input type="text" name="userName"  required  ng-trim="true" ng-value="user.userName" ng-model="user.userName">
					<p class="help-block error" ng-show="form.userName.$error.required && form.userName.$dirty">员工姓名不为空</p>
				</div>
			</div>	
		    <div class="control-group">
				<label class="control-label">所在部门:</label>
				<div class="controls">
					<span ng-bind="department.name"></span>
				</div>
			</div>	
		    <div class="form-actions ">
		    	<button type="submit" class="btn btn-primary" ng-disabled="form.$invalid"><i class="icon-edit icon-white"></i>修改</button>
		    </div>		
			
   		</form>
	 </div>
  </body>
</html>
