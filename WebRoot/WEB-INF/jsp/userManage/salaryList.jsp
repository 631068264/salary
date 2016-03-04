<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 
<!DOCTYPE HTML>
<html ng-app>
  <head>
  	<%@ include file="/WEB-INF/jsp/public/common.jspf" %>
    <title>查看个人工资</title>
	<script>
		var formCtrl = ['$scope','$http',function($scope,$http){
			$scope.topUrl="../public/top.html";
			
			$http.get('./getSalary').success(function(data){
				$scope.user = data.user;
				$scope.salary = data.salary;
				
			});

		}];
	</script>
  </head>
  
  <body >
  	<div class="container" ng-controller="formCtrl">
  		<header ng-include="topUrl" style="margin-bottom:10%"></header>
	    <table class="table table-hover" >
	    	<caption><h3>个人工资表</h3></caption>
	    	<tr>
	    		<th>总收入</th>
	    		<th>实际收入</th>
	    		<th>罚金</th>
	    		<th>基本工资</th>
	    		<th>福利</th>
	    		<th>奖金</th>
	    	</tr>
	    	<tr >
	    		<td ng-bind="salary.total"></td>
	    		<td ng-bind="salary.pay"></td>
	    		<td ng-bind="salary.deduct"></td>
	    		<td ng-bind="salary.basePay"></td>
	    		<td ng-bind="salary.welfare"></td>
	    		<td ng-bind="salary.bonus"></td>
	    	</tr>
	    	
	    </table>
		<div class="row">
	    	<a ng-href="${pageContext.request.contextPath}/userManage/{{user.id}}/print" class="btn pull-right">
	    		<i class=" icon-print"></i>打印个人工资表
			</a>
		</div>
	    
		<div class="alert alert-info ">
		    <h4>工资管理情况说明:</h4>
		    <ol >
		    	<li>每个月15日后罚金清零</li>
		    	<li>实际收入 = 总收入 - 罚金</li>
		    	<li>总收入 = 基本工资 + 福利 + 奖金</li>
		    </ol>
		</div>
		
	</div>
  </body>
</html>
