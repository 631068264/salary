<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

 
<!DOCTYPE HTML>
<html ng-app>
  <head>
  	<%@ include file="/WEB-INF/jsp/public/common.jspf" %>
    <title>员工列表</title>
	<script>
		var formCtrl = ['$scope','$http',function($scope,$http){
			$scope.topUrl="../public/top.html";
			//删除员工
			$scope.del = function(uid){
				var b = window.confirm("确认删除吗？");
				if (b) {
					window.location.href = "./"+ uid+"/del";
				}
			}
			
			
			//分页模块
			$scope.pageView ="../public/pageView.html";
			var url = "./getUserList";//获取地址
			//获取首页
			$http.get(url).success(function(data){
				$scope.pager = data;//页面数据
				
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
					$scope.pager = data.pager;	//页面数据
					
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
	    <table class="table table-striped table-condensed">
	    	<caption><h2>员工列表</h2></caption>
	    	<tr>
	    		<th>员工编号</th>
	    		<th>员工姓名</th>
	    		<th>所属部门</th>
	    		<th>岗位</th>
	    		<th>编辑</th>
	    		<th>删除</th>
	    	</tr>
	    	
	    	<tr ng-repeat="list in pager.recordList ">
	    		<td ng-bind="list.user.userID"></td>
	    		<td ng-bind="list.user.userName"></td>
	    		<td ng-bind="list.department.name"></td>
	    		<td ng-bind="list.role.description"></td>
				
	    		<td>
	    			<a ng-href="${pageContext.request.contextPath}/adminManage/{{list.user.id}}/editUI"
					class="btn"><i class="icon-edit"></i>编辑</a>
	    		</td>
	    		<td>
	    			<a href="javascript:void(0);" ng-click="del(list.user.id)" class="btn">
	    				<i class="icon-trash"></i>删除
					</a>
	    		</td>
	    	</tr>
	    </table>
		<div class="row">
		    <a href="${pageContext.request.contextPath}/adminManage/addUI" class="btn btn-primary pull-right">
		    	<i class="icon-plus icon-white"></i>新增员工
			</a>
		</div>
		
		<!--分页信息-->
		<div ng-include="pageView"></div>
	</div>
  </body>
</html>
