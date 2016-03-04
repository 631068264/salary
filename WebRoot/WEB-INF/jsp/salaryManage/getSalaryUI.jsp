<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 
<!DOCTYPE HTML >
<html ng-app>
  <head>
  	<%@ include file="/WEB-INF/jsp/public/common.jspf" %>
    <title>查看部门工资</title>
	<script>
		var formCtrl = ['$scope','$http',function($scope,$http){
			$scope.topUrl="../public/top.html";
			
			//部门信息
			$http.get('./getDepartmentInfo').success(function(data){
				$scope.avg = data;
			});
			
			//修改个人工资
			$scope.edit = function(salary){
				$http.get('./'+salary.id+'/edit',{params:salary}).success(function(){
					$http.get('./getDepartmentInfo').success(function(data){
						//更新平均工资
						$scope.avg = data;
					});
				});
			}
			
			//总收入
			$scope.total = function(a,b,c){
				return parseInt(a)+parseInt(b)+parseInt(c);
			}
			//实际收入
			$scope.pay = function(a,b,c,d){
				return parseInt(a)+parseInt(b)+parseInt(c) - d;
			}
			
			//分页模块
			$scope.pageView ="../public/pageView.html";
			var url = "./getSalary";//获取地址
			//获取首页
			$http.get(url).success(function(data){
				console.debug(data);
				$scope.pager = data;	//页面数据
				
				//转到功能
				$scope.selectArray = new Array();
				for(var i = 1,j=0;i<=$scope.pager.pageCount ; i++){
					$scope.selectArray[j++] = i;
				}
				$scope.pn = $scope.pager.currentPage;
				
				//页数显示
				$scope.pageArray = new Array();
				for(var i = $scope.pager.beginPageIndex,j=0;i<=$scope.pager.endPageIndex;i++){
					$scope.pageArray[j++] = i;
				}
		
			});
			// 获取指定页
			$scope.gotoPage = function(page){
				//获取首页
				$http.get(url,{params:{currentPage:page}}).success(function(data){
					$scope.pager = data;	//页面数据
					
					//转到功能
					$scope.selectArray = new Array();
					for(var i = 1,j=0;i<=$scope.pager.pageCount ; i++){
						$scope.selectArray[j++] = i;
					}
					$scope.pn = $scope.pager.currentPage;
					
					//页数显示
					$scope.pageArray = new Array();
					for(var i = $scope.pager.beginPageIndex,j=0;i<=$scope.pager.endPageIndex;i++){
						$scope.pageArray[j++] = i;
					}
			
				})
			}
		}];
	</script>
   
  </head>
  
  <body >
  	<div class="container" ng-controller="formCtrl">
  		<header ng-include="topUrl" style="margin-bottom:10%"></header>
   		<input type="hidden" name="uid" value="${user.id }">
	    <table class="table table-condensed table-striped">
	    	<caption><h2>员工工资汇总表</h2></caption>
	    	<tr>
	    		<th>员工编号</th>
	    		<th>员工姓名</th>
	    		<th>总收入</th>
	    		<th>实际收入</th>
	    		<th>罚金</th>
	    		<th>基本工资</th>
	    		<th>福利</th>
	    		<th>奖金</th>
	    		<th>操作</th>
	    	</tr>
	    	
	    	<tr ng-repeat="list in pager.recordList">
	   
	    		<td ng-bind="list.user.userID"></td>
	    		<td ng-bind="list.user.userName"></td>
	    		<td ng-bind="total(list.salary.basePay,list.salary.welfare,list.salary.bonus)|number:2"></td>
	    		<td ng-bind="pay(list.salary.basePay,list.salary.welfare , list.salary.bonus,list.salary.deduct)|number:2"></td>
		    	<td>
	    			<div class="input-prepend">
	    				<span class="add-on">&yen;</span><input type="number" name="deduct"  
						ng-value="list.salary.deduct|number:2" ng-model="list.salary.deduct" class="span1">
					</div>
	    		</td>
	    		<td>
	    			<div class="input-prepend">
	    			<span class="add-on">&yen;</span><input type="number" name="basePay" 
					ng-value="list.salary.basePay|number:2" ng-model="list.salary.basePay" class="span1">
					</div>
	    		</td>
	    		<td>
	    			<div class="input-prepend">
	    			<span class="add-on">&yen;</span><input type="number" name="welfare"  
					ng-value="list.salary.welfare|number:2" ng-model="list.salary.welfare" class="span1">
					</div>
	    		</td>
	    		<td>
	    			<div class="input-prepend">
	    			<span class="add-on">&yen;</span><input type="number" name="bonus"  
					ng-value="list.salary.bonus|number:2" ng-model="list.salary.bonus" class="span1">
					</div>
	    		</td>
	    		<td>
	    			<a href="javascript:void(0);" ng-click="edit(list.salary)" class="btn">
	    				<i class="icon-edit"></i>保存</a>
	    		</td>
			</tr>
	    	
	    	<tr style="text-align:left;">
	    		<th colspan="9">
	    			部门平均工资 : &yen;&nbsp;&nbsp;{{avg|number:2}}
	    		</th>
	    	</tr>
	    	
	    </table>
	    <div class="row">	
	    	<a href="${pageContext.request.contextPath}/salaryManage/print" class="btn btn-primary pull-right">
	    		<i class=" icon-print icon-white"></i>导出部门工资表
			</a>
	    </div>	
		
		 <!--分页信息-->
		<div ng-include="pageView"></div>
		
	     <div class="alert alert-info">
		    <p >工资管理情况说明:</p>
		    <ol >
		    	<li>每个月15日后罚金清零</li>
		    	<li>实际收入 = 总收入 - 罚金</li>
		    	<li>总收入 = 基本工资 + 福利 + 奖金</li>
		    </ol>
		</div>
	</div>
  </body>
</html>
