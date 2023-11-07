<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="${pageContext.request.contextPath}/static/css/style.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="${pageContext.request.contextPath}/static/js/jquery.js"></script>
<script type="text/javascript">
	$(function(){	
		//顶部导航切换
		$(".nav li a").click(function(){
			$(".nav li a.selected").removeClass("selected")
			$(this).addClass("selected");
		})	
	})

</script>
</head>
<body style="background:url(${pageContext.request.contextPath}/static/images/topbg.gif) repeat-x;">

    <div class="topleft">
    <!-- <a href="main.html" target="_parent"> --><img src="${pageContext.request.contextPath}/static/images/logo.png" title="系统首页" /><!-- </a> -->
    </div>       
   
            
    <div class="topright">    
    <ul>
    <li><span><img src="${pageContext.request.contextPath}/static/images/help.png" title="帮助"  class="helpimg"/></span></li>
    <!-- <li><a href="#">关于</a></li> -->
    <li><a href="${pageContext.request.contextPath}/Logout" target="_parent">退出</a></li>
    </ul>
     
    <div class="user">
    <span>${user.truename }</span>
    <i>欢迎你！</i>
    </div>    
    
    </div>

</body>
</html>