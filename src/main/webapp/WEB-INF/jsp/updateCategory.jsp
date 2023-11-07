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
 	<li><a href="${pageContext.request.contextPath}/categoryList">商品类别管理</a></li>
    <li><a href="${pageContext.request.contextPath}/updateCategoryBefore?id=${tempCategory.id}">更新商品类别</a></li>
    </ul>
    </div>
    
    <div class="formbody">
    
    <div class="formtitle"><span>更新商品类别</span></div>
    
    <ul class="forminfo">
    <form action="${pageContext.request.contextPath}/updateCategory" method="post" id="form1">
    	<input type="text" id="id" name="id" value="${tempCategory.id}" style="display: none"/>
	    <li><label>名称</label><input name="name" type="text" class="dfinput" value="${tempCategory.name }"/><font style="color: red">&nbsp;&nbsp;&nbsp;*</font>
	    </li>
	    <li><label>积压值</label><input name="maxamount" type="text" class="dfinput" value="${tempCategory.maxamount }"/><font style="color: red">&nbsp;&nbsp;&nbsp;*</font>
	    </li>
	    <li><label>缺货值</label><input name="minamount" type="text" class="dfinput" value="${tempCategory.minamount }"/><font style="color: red">&nbsp;&nbsp;&nbsp;*</font>
	    </li>
	     <li><label>出货平均缺货值</label><input name="sellMaxamount" type="text" class="dfinput" value="${tempCategory.sellMaxamount }"/><font style="color: red">&nbsp;&nbsp;&nbsp;*</font>
	    </li>
	    <li><label>出货平均积压值</label><input name="sellMinamount" type="text" class="dfinput" value="${tempCategory.sellMinamount }"/><font style="color: red">&nbsp;&nbsp;&nbsp;*</font>
	    </li>
    </form>
    <li><label>&nbsp;</label><input name="" type="button" class="btn" value="提交保存" onclick="submitData()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="" type="button" class="btn" value="取消" onclick="back('${pageContext.request.contextPath}/categoryList')"/></li>
     <li>
    	<c:if test="${message !='' }">
    		<font color="red">${message}</font>
    	</c:if>
    </li>
    </ul>
    
    
    </div>


</body>

</html>
