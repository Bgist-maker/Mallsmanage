<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="${pageContext.request.contextPath}/static/css/style.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/static/css/pagination.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="${pageContext.request.contextPath}/static/js/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/pagination.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
	  $(".click").click(function(){
	  $(".tip").fadeIn(200);
	  });
	  
	  $(".tiptop a").click(function(){
	  $(".tip").fadeOut(200);
	});
	
	  $(".sure").click(function(){
	  $(".tip").fadeOut(100);
	});
	
	  $(".cancel").click(function(){
	  $(".tip").fadeOut(100);
	});
	
	});
 	$(document).ready(function(){
 		 $(".tiptop a").click(function(){
 		  	$(".tip").fadeOut(200);
 		 });
	
 	  	 $(".sure").click(function(){
 	        $.ajax({
 				url : "${pageContext.request.contextPath}/deleteRoles?ids="+$("#saveIds").val(),
 				type : 'post',
 				async : false, 
 				success : function(objs) {
 					if(objs.result==1){
 						window.location.href="${pageContext.request.contextPath}/roleList";
 					}else{
 						alert(objs.message);
 					}
 				}
 			});
 	        $(".tip").fadeOut(100);
 	     });
	
 		 $(".cancel").click(function(){
 		    $(".tip").fadeOut(100);
 		 });
 	 });
	
 	function modifyAuthority(roleid) {
 		window.location.href="${pageContext.request.contextPath}/modifyAuthorityBefore?roleid="+roleid;
 	}
	
 	function addRole(){
 		window.location.href="${pageContext.request.contextPath}/addRoleBefore"
 	}
 	function checkAll(){
 		if($('#all').is(':checked')) {
 			$("input[name=sub]").attr("checked","checked");
 		}else{
 			$("input[name=sub]").removeAttr("checked");
 		}
 	}
 	function delSelectRole(){
 		if($("input[name=sub]:checked").length==0){
 			alert("请选择要删除的用户！")
 			return;
 		}
 		$(".tip").fadeIn(200);
 		var ids = "";
 		$("input[name=sub]:checked").each(function(){
 			ids=ids+","+"'"+$(this).val()+"'"
 		 });
 		 $("#saveIds").val(ids);
 	}
 	
	function updateRole() {
		if($("input[name=sub]:checked").length==0){
			alert("请选择要更新的角色！")
			return;
		}else if($("input[name=sub]:checked").length>1){
			alert("请选择有且只有一条更新的角色！")
			return;
		}
		window.location.href="${pageContext.request.contextPath}/updateRoleBefore?id="+$("input[name=sub]:checked").val();
	}
	
	function getPage(pageCount){
		window.location.href="${pageContext.request.contextPath}/roleList?pageCount="+pageCount
	}
	
	$(function(){
 		var onPagechange = function(page){
 			getPage(page);
		}
 		var taotal=$("#total").val();
 		var pageCount=$("#pageCount").val();
		var obj = {
		    wrapid:'page', //页面显示分页器容器id
		    total:taotal,//总条数
		    pagesize:10,//每页显示10条
		    currentPage:pageCount,//当前页
		    onPagechange:onPagechange,
		    //btnCount:7 页数过多时，显示省略号的边界页码按钮数量，可省略，且值是大于5的奇数
		}
		pagination.init(obj);
 	});
</script>
</head>
<body>
	<input type="text" value="${total}" style="display: none" id="total"/>
	<input type="text" value="${pageCount}" style="display: none" id="pageCount"/>
	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="${pageContext.request.contextPath}/index">首页</a></li>
    <li><a href="${pageContext.request.contextPath}/roleList">角色管理</a></li>
    </ul>
    </div>
    
    <div class="rightinfo">
    
    <div class="tools">
    
    	<ul class="toolbar">
        <li onclick="addRole()"><span ><img src="${pageContext.request.contextPath}/static/images/t01.png" /></span>添加</li>
        <li onclick="updateRole()"><span><img src="${pageContext.request.contextPath}/static/images/t02.png" /></span>修改</li>
        <li class="click" onclick="delSelectRole()"><span><img src="${pageContext.request.contextPath}/static/images/t03.png" /></span>删除</li>
        </ul>
    
    </div>
    
    
    <table class="tablelist">
    	<thead>
    	<tr>
        <th><input name="" type="checkbox" value="" onclick="checkAll()" id="all"/>全选</th>
        <th>编号<i class="sort" onclick="sortData()"><img src="${pageContext.request.contextPath}/static/images/px.gif" /></i></th>
        <th>角色名称</th>
        <th>操作</th>
        </tr>
        </thead>
        
        <c:forEach items="${roles }" var="role">
        	<tbody>
        	 <tr>
		        <td><c:if test="${role.id!=1}"><input name="sub" type="checkbox" value="${role.id}" /></c:if></td>
		        <td>${role.id}</td>
		        <td>${role.name }</td>
		        <td>
			        <c:if test="${role.id!=1}">
			        	<a href="javaScript:void(0)" onclick="modifyAuthority('${role.id}')" class="tablelink">修改权限</a>
			        </c:if>
		        </td>
	        </tr> 
	        </tbody>
        </c:forEach>
       
    </table>
     <div class="pagin">
    	<div class="message">共<i class="blue">${total }</i>条记录，当前显示第&nbsp;<i class="blue">${pageCount }&nbsp;</i>页</div>
	    <div id="page" class="paginList">
	    	
	    </div>
    </div>
    <div class="tip">
    	<div class="tiptop"><span>提示信息</span><a></a></div>
      <input type="text" id="saveIds" style="display: none"></input>
      <div class="tipinfo">
        <span><img src="${pageContext.request.contextPath}/static/images/ticon.png" /></span>
        <div class="tipright">
        <p>是否确认对信息的删除?</p>
        <cite>如果是请点击确定按钮 ，否则请点取消。</cite>
        </div>
        </div>
        
        <div class="tipbtn">
        <input name="" type="button"  class="sure" value="确定" />&nbsp;
        <input name="" type="button"  class="cancel" value="取消" />
        </div>
    
    </div>
    </div>
    
    <script type="text/javascript">
		$('.tablelist tbody tr:odd').addClass('odd');
	</script>
</body>
</html>