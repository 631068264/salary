<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html ng-app>
  <head>
  	<%@ include file="/WEB-INF/jsp/public/common.jspf" %>
    <title>修改部门信息</title>
	<script>
		var formCtrl = ['$scope','$http',function($scope,$http){
			$scope.topUrl="../public/top.html";
		}];
	</script>
	<style>
	 	.error{color:red}
	 </style>
  </head>
  
  <body >
  	<div class="container" ng-controller="formCtrl">
  		<header ng-include="topUrl" style="margin-bottom:10%"></header>
	   		<form action="${pageContext.request.contextPath}/departmentManage/add" 
			method="post" class="form-horizontal" novalidate name="form">
		
			<legend>新增部门</legend>
			
			<div class="control-group">
				<label class="control-label">部门名称:</label>
				<div class="controls">
					<input type="text" name="name"  required  ng-trim="true" 
					ng-value="department.name" ng-model="department.name" placeholder="必须填">
					<p class="help-block error" ng-show="form.name.$error.required && form.name.$dirty">员工编号不为空</p>
				</div>
			</div>	
			
			<div class="control-group">
				<label class="control-label">部门描述:</label>
				<div class="controls">
					<textarea cols="60" rows="10"  name="description" ng-model="department.description" ng-trim="true" ></textarea>
				</div>
			</div>	
		    	
		    	
		    <div class="form-actions ">
		    	<button type="submit" class="btn btn-primary" ng-disabled="form.$invalid"><i class="icon-edit icon-white"></i>保存</button>
		    </div>
			
   		</form>
		
  	</div>
	
	
	
  
  </body>
</html>
