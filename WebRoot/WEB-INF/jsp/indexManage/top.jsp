<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html ng-app>
  <head>
  	<%@ include file="/WEB-INF/jsp/public/common.jspf" %>
  	
    <title>顶层</title>
	<script>
		var MenuCtrl = ['$scope','$http',function($scope,$http){
			$http.get('./getMenu').success(function(data){
				console.debug(data);
				$scope.privilegeList = data.privilegeList;
				$scope.user = data.user;
			});
		}];
	</script>
  </head>
  
  <body>
  	<div class="container" ng-controller="MenuCtrl">
  		<div class="navbar navbar-inverse navbar-fixed-top">
  			<div class="navbar-inner">
  			<div class="container">
  				<!--导航响应-->
				<a href="#" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse" >
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</a>
  				<a class="brand" href="#">工资管理系统</a>
				<div class="nav-collapse">	
				<!-- 菜单导航-->
				<ul class="nav">
				<!--顶级菜单 -->
					<li class="dropdown" ng-repeat="list in privilegeList">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown" ng-bind="list.name"><span class="caret"></span></a>
						<!--子菜单-->
						<ul class="dropdown-menu" >
							
							<li ng-repeat="c in list.children">
								<a target="main" ng-href="${pageContext.request.contextPath}{{c.url}}" ng-bind="c.name"></a>
							</li>
							
						</ul>
					
					</li>
				</ul><!--end nav -->
				
				<!--其他用户信息-->
				<ul class="nav pull-right">
					<li><a href="#"><i class="icon-user icon-white"></i> {{user.userName}}</a></li>
					<li><a href="javascript: window.parent.main.location.reload(true);">刷新</a></li>
					<li class="divider-vertical"></li>
					<li><a href="${pageContext.request.contextPath}/logout" target="_parent">退出</a></li>					
				</ul>
				
  			</div><!-- end nav-collapse-->
  			</div><!-- end container-->
  			</div><!-- navbar-inner-->
  		</div>
  		
  		
  		
  		
  		
  		
  	</div>
  
   		
  </body>
</html>
