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
	function submitData(){
		$("#form1").submit();
	}
	function back(url){
		window.location.href=url;
	}
	
</script>
</head>

<body>
	<div class="place">
    <span>位置</span>
    <ul class="placeul">
    <li><a href="${pageContext.request.contextPath}/index">首页</a></li>
 	<li><a href="${pageContext.request.contextPath}/unitList">计量单位管理</a></li>
    <li><a href="${pageContext.request.contextPath}/updateUnitBefore?id=${tempUnit.id}">修改计量单位</a></li>
    </ul>
    </div>
    
    <div class="formbody">
    
    <div class="formtitle"><span>修改计量单位</span></div>
    
    <ul class="forminfo">
    <form action="${pageContext.request.contextPath}/updateUnit" method="post" id="form1">
    	<input type="text" id="id" name="id" value="${tempUnit.id}" style="display: none"/>
	    <li><label>帐号</label><input name="name" type="text" class="dfinput" value="${tempUnit.name }"/><font style="color: red">&nbsp;&nbsp;&nbsp;*</font>
	    </li>
    </form>
    <li><label>&nbsp;</label><input name="" type="button" class="btn" value="提交保存" onclick="submitData()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="" type="button" class="btn" value="取消" onclick="back('${pageContext.request.contextPath}/unitList')"/></li>
     <li>
    	<c:if test="${message !='' }">
    		<font color="red">${message}</font>
    	</c:if>
    </li>
    </ul>
    
    
    </div>


</body>

</html>
