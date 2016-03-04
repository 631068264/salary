<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html ng-app>
  <head>
  <%@ include file="/WEB-INF/jsp/public/common.jspf" %>
    <title>修改个人信息</title>
      <script>
   		var formCtrl = ['$scope','$http',function($scope,$http){
			$scope.topUrl="../../public/top.html";
			$scope.role={r:{}};
			
			$http.get('./editInfo').success(function(data){
				
				$scope.departments = data.departments;
				$scope.departmentId = data.departmentId;
				
				$scope.roles = data.roles;
				$scope.role.r = data.roleIds;
				
				$scope.salary = data.salary;
				$scope.user = data.user;
			});
			
			
			$scope.show = function(){
				if($scope.role.r > 0){
					return false;
				}
				return true;
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
   		<form action="${pageContext.request.contextPath}/adminManage/edit" 
		method="post" class="form-horizontal" novalidate name="form" >
   			<input type="hidden" name="sid" ng-value="salary.id">
   			<input type="hidden" name="uid" ng-value="user.id">
   			<input type="hidden" name="departmentId" ng-value="departmentId">
			
			
			<legend>编辑员工</legend>
			<div class="control-group">
				<label class="control-label">员工编号:</label>
				<div class="controls">
					<input type="text" name="userID"  required  ng-trim="true" 
					ng-value="user.userID" ng-model="user.userID" placeholder="必须填">
					<p class="help-block error" ng-show="form.userID.$error.required && form.userID.$dirty">此框不为空</p>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">员工姓名:</label>
				<div class="controls">
					<input type="text" name="userName"  required  ng-trim="true" 
					ng-value="user.userName" ng-model="user.userName" placeholder="必须填">
					<p class="help-block error" ng-show="form.userName.$error.required && form.userName.$dirty">此框不为空</p>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">所在部门:</label>
				<div class="controls">
					<select    ng-model="departmentId"
						class="span2" required ng-options="d.id as d.name for d in departments" >
		    		</select>
					<p class="help-block error" ng-show="form.departmentId.$error.required ">此框不为空</p>
				</div>
			</div>
			
			<div class="control-group">
				<label class="control-label">权限:</label>
				<div class="controls">
					
					<label ng-repeat="r in roles"  class="radio inline">
						<input type="radio" name="roleIds" ng-model="role.r" ng-value="r.id" >
						{{r.description}}
					</label>
					<p class="help-block error" ng-show="show()">此框不为空</p>
				</div>
			</div>
		    
		    	
	    	<tr>
	    		<td colspan="2">
			    	<table>
			    		<tr>
				    		<th>基本工资</th>
				    		<th>福利</th>
				    		<th>奖金</th>
			    		</tr>
			    		<tr>
				    		<td>
				    			<input type="number"  name="basePay" ng-value="salary.basePay|number:2" ng-model="salary.basePay" required placeholder="必须填">
								<p class="help-block error" ng-show="form.basePay.$error.required && form.basePay.$dirty">此框不为空</p>
				    		</td>
				    		<td>
				    			<input type="number"  name="welfare" ng-value="salary.welfare|number:2" ng-model="salary.welfare" required placeholder="必须填">
								<p class="help-block error" ng-show="form.welfare.$error.required && form.welfare.$dirty">此框不为空</p>
				    		</td>
				    		<td>
				    			<input type="number"  name="bonus" ng-value="salary.bonus|number:2" ng-model="salary.bonus" required placeholder="必须填">
								<p class="help-block error" ng-show="form.bonus.$error.required && form.bonus.$dirty">此框不为空</p>
				    		</td>
			    		</tr>
			    	</table>
	    		</td>
	    	</tr>
		    	
		    <div class="form-actions ">
		    	<button type="submit" class="btn btn-primary" ng-disabled="form.$invalid || show()"><i class="icon-edit icon-white"></i>保存</button>
		    </div>
   </form>
   </div>
  </body>
</html>
