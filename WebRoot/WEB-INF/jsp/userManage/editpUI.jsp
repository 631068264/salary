<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

 
<!DOCTYPE HTML>
<html ng-app>
  <head>
  	<%@ include file="/WEB-INF/jsp/public/common.jspf" %>
    <title>修改个人密码</title>
	<script>
		var formCtrl = ['$scope','$http',function($scope,$http){
			$scope.topUrl="../public/top.html";
			$scope.password ="";
			$scope.error ="";
			
			$scope.a = false;
			var flag = false;
			$scope.show = function(){
					var p = $scope.password;
					if( !$scope.a && $scope.password.length >= 5){
					$http.get('./password',{params:{password:p}}).success(function(data){
						if(data=="false"){
							flag = true;
						}else{
							flag = false;
						}
						$scope.a = true;
					});
				}
				return flag;
			}
			
			
		}];
	</script>
	 <style>
	 	.error{color:red}
	 </style>
  </head>
  
  <body >
  	<div class="container" ng-controller="formCtrl">
  		<header ng-include="topUrl" style="margin-bottom:10%"></header>
		<div class="row">
   		<form name="form" action="${pageContext.request.contextPath}/userManage/editp" method="post" class="form-horizontal span5 offset3" novalidate >
   			<input type="hidden" name="uid" value="${user.id }">
				<legend>密码修改</legend>
	    		 <div class="control-group" >
					<label class="control-label">旧密码:</label>
					<div class="controls">
						<input type="password" name="password"  required  ng-trim="true" ng-value="password" ng-model="password" ng-change="a=false">
						<p class="help-block error" ng-show="form.password.$error.required && form.password.$dirty">不能为空</p>
						<p class="help-block error"  ng-show="show()">旧密码错误</p>
					</div>
				</div>	
				
	    		 <div class="control-group" >
					<label class="control-label">新密码:</label>
					<div class="controls">
						<input type="text" name="newpassword"  required  ng-trim="true" ng-model="newpassword" ng-minlength="5" placeholder="密码多于5位">
						<p class="help-block error" ng-show="form.newpassword.$error.required && form.newpassword.$dirty">不能为空</p>
						<p class="help-block error" ng-show="form.newpassword.$error.minlength && form.newpassword.$dirty">密码多于5位</p>
						<p class="help-block error" >${error}</p>
					</div>
				</div>	
				
	    		
	    		<div class="form-actions " >
		    		<button type="submit" class="btn btn-primary pull-right" ng-disabled="form.$invalid || show() || form.newpassword.$error.minlength"><i class="icon-edit icon-white"></i>修改</button>
		    	</div>	
	    
   			</form>
		</div>	
		<div class="row">	
			<div class="alert alert-info">
		  		<h4>提示</h4>
		  		tip：默认密码是员工编号
		  	</div>
		</div>	
	    </div>
  </body>
</html>
