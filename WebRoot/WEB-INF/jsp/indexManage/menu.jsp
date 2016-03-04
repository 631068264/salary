<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 <%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/style/menu.css" />
  <script language="javascript" src="${pageContext.request.contextPath}/script/jquery.js"></script>
  
   <title>菜单页面</title>
   <script type="text/javascript">
   		function menuClick(div){
   			//子菜单
   			$(".level2").not($(div).next()).hide();
   			$(div).next().toggle();
   			
   		}
   </script>
   
  </head>
  
  <body>
  	<div id="left">
   		<div id="menu">
   			<ul class="level1">
   				<c:forEach items="${privilegeList }" var="p">
   					<li style="padding:10px;">
   						<div onClick="menuClick(this);">
   							<b>${p.name }</b>
   						</div>
   					
   					
   					<ul style=" display: none;"  class="level2">
   						<c:forEach items="${p.children}" var="c">
   						<li >
   							 	<div>
    								<a target="right" href="<spring:url value="${c.url }"/>">${c.name }</a>
   							 	</div>
   						</li>
   						</c:forEach>
   					</ul>
   				</li>	
   				</c:forEach>
   			</ul>
   		</div>
   	</div>
  </body>
</html>
