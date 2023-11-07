<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${title}</title>
<link href="${pageContext.request.contextPath}/static/css/style.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="${pageContext.request.contextPath}/static/js/jquery.js"></script>
<script type="text/javascript">
	$(function(){
		$.ajax({
			url : '${pageContext.request.contextPath}/getAllRoles',
			type : 'post',
			async : false, //默认为true 异步   
			success : function(objs) {
				$("#roleid").empty();
				for(var i =0;i<objs.length;i++) {
					$("#roleid").append("<option value='"+objs[i].id+"'>"+objs[i].name+"</option>");
				}
				var roleid = $("#tempRoleid").val();
				$("#roleid").val(roleid);
			}
		});
	})
	
	function submitData(){
		$("#form1").submit();
	}
	function back(url){
		window.location.href=url;
	}
	
</script>
</head>

<body>
	<input type="text" id="tempRoleid" value="${tempUser.roleid}" style="display: none"/>
	<div class="place">
    <span>位置</span>
    <ul class="placeul">
    <li><a href="${pageContext.request.contextPath}/index">首页</a></li>
      	<li><a href="${pageContext.request.contextPath}/userList">账号管理</a></li>
    	<li><a href="${pageContext.request.contextPath}/addUserBefore?id=${tempUser.id}">更新账号</a></li>
    </ul>
    </div>
    
    <div class="formbody">
    
    <div class="formtitle"><span>更新账号</span></div>
    
    <ul class="forminfo">
    <form action="${pageContext.request.contextPath}/updateUser" method="post" id="form1">
    	<input type="text" id="id" name="id" value="${tempUser.id}" style="display: none"/>
	    <li><label>帐号</label><input name="userno" type="text" class="dfinput" value="${tempUser.userno }"/><font style="color: red">&nbsp;&nbsp;&nbsp;*</font>
	    </li>
	    <li><label>姓名</label><input name="truename" type="text" class="dfinput" value="${tempUser.truename }"/><font style="color: red">&nbsp;&nbsp;&nbsp;*</font>
	    </li>
	    <li><label>密码</label><input name="password" type="password" class="dfinput" />
	    </li>
	    <li>
	    	<label>角色</label>
    		<select id="roleid" name="roleid">
			</select>
	    </li>
    </form>
    <li><label>&nbsp;</label><input name="" type="button" class="btn" value="提交保存" onclick="submitData()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="" type="button" class="btn" value="取消" onclick="back('${pageContext.request.contextPath}/userList')"/></li>
     <li>
    	<c:if test="${message !='' }">
    		<font color="red">${message}</font>
    	</c:if>
    </li>
    </ul>
    
    
    </div>


</body>

</html>
