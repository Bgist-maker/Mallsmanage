<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>商场库存管理系统</title>
<link href="${pageContext.request.contextPath}/static/css/style.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="${pageContext.request.contextPath}/static/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/static/js/cloud.js" type="text/javascript"></script>
<script language="javascript">
	$(function(){
	    $('.loginbox').css({'position':'absolute','left':($(window).width()-692)/2});
		$(window).resize(function(){  
	   	 	$('.loginbox').css({'position':'absolute','left':($(window).width()-692)/2});
    	});
	});
    $(function(){
    	var userno = getCookieValue("userno");
    	if(userno!==""){
        	$("#userno").val(userno);//
    	}
        var password = getCookieValue("password");
        if(password!==""){
	        $("#password").val(password);
        }
    })
    
    function login(){
        var userno = $("#userno").val();//jqury 获取输入框值的语法。
        var password = $("#password").val();
        if(userno=="" || password==""){
        	alert("请输入账号或者密码！")
        }
        if( document.getElementById("saveCookie").checked){   
            setCookie("userno",userno,24,"/");
            setCookie("password",password,24,"/");
            $("#form1").submit();
        }else{ 
            //如果没有选中记住密码,则立即过期
            deleteCookie(getCookieValue("userno"),"/");
            deleteCookie(getCookieValue("password"),"/");
            $("#form1").submit();
        }    
	}
		
	function getCookieValue(name){
         var name = escape(name);
         //读cookie属性，这将返回文档的所有cookie
         var allcookies = document.cookie;      
         //查找名为name的cookie的开始位置
          name += "=";
         var pos = allcookies.indexOf(name);   
         //如果找到了具有该名字的cookie，那么提取并使用它的值
         if (pos != -1){                                            
			//如果pos值为-1则说明搜索"version="失败
             var start = pos + name.length;                 
			//cookie值开始的位置
			 var end = allcookies.indexOf(";",start);       
			//从cookie值开始的位置起搜索第一个";"的位置,即cookie值结尾的位置
			if (end == -1) end = allcookies.length;       
			//如果end值为-1说明cookie列表里只有一个cookie
			var value = allcookies.substring(start,end);
			//提取cookie的值
			return (value);                          
		//对它解码     
		   }  
		else return "";                              
		//搜索失败，返回空字符串
	}
	function setCookie(name,value,hours,path){
         var name = escape(name);
         var value = escape(value);
         var expires = new Date();
         expires.setTime(expires.getTime() + hours*60*60*1000);
         path = path == "" ? "" : ";path=" + path;
         _expires = (typeof hours) == "string" ? "" : ";expires=" + expires.toUTCString();
         document.cookie = name + "=" + value + _expires + path;
	}

	//删除cookie
	function deleteCookie(name,path){
	    var name = escape(name);
	    var expires = new Date(0);
	    path = path == "" ? "" : ";path=" + path;
	    document.cookie = name + "="+ ";expires=" + expires.toUTCString() + path;
	}
</script> 
</head>
<body style="background-color:#1c77ac; background-image:url(${pageContext.request.contextPath}/static/images/light.png); background-repeat:no-repeat; background-position:center top; overflow:hidden;">



    <div id="mainBody">
      <div id="cloud1" class="cloud"></div>
      <div id="cloud2" class="cloud"></div>
    </div>  


<div class="logintop">    
    <span>欢迎登录商场库存管理系统</span>    
<!--     <ul>
    <li><a href="#">回首页</a></li>
    <li><a href="#">帮助</a></li>
    <li><a href="#">关于</a></li>
    </ul>  -->   
    </div>
    
    <div class="loginbody">
    
    <span class="systemlogo"></span> 
       
    <div class="loginbox">
    <form action="${pageContext.request.contextPath}/loginValidate" id="form1" method="post">    
	    <ul>
		    <li><input id="userno" name="userno" type="text" class="loginuser" onclick="JavaScript:this.value=''"/></li>
		    <li><input id="password" name="password" type="password" class="loginpwd" onclick="JavaScript:this.value=''"/></li>
		    <li><input name="" type="button" class="loginbtn" value="登录"  onclick="login()"  /><label><input name="" type="checkbox" value="" checked="checked" id="saveCookie"/>记住密码</label></li>
		    <li>
    		<c:if test="${message !='' }">
    			<font color="red">${message}</font>
    		</c:if>
    </li>
		 </ul>
    </form>
    
    
    </div>
    
    </div>
    
    
    
    <div class="loginbm">版权所有  商城库存管理系统 <a href="#"></a>wcz </div>
	
    
</body>
</html>