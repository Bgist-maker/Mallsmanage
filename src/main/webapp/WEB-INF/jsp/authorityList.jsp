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
<style>
	form>li{
		display: inline;
		padding:10px;
	}
</style>
<script type="text/javascript">
	function submitData(){
		var actionids = "";
		$("input[type='checkbox']:checked").each(function(){
			actionids = actionids +$(this).val()+","
		});
		$("#actionids").val(actionids);
		$("#form1").submit();
	}
	function back(url){
		window.location.href=url;
	}
	$(function(){
		debugger;
		var roleid = $("#roleid").val();
		$("input[type='checkbox']").each(function(){
			if($("#"+$(this).val()).text()==roleid){
				$(this).attr("checked",true);
			}
		});
	});
</script>
</head>

<body>
	<div class="place">
    <span>位置</span>
    <ul class="placeul">
    <li><a href="${pageContext.request.contextPath}/index">首页</a></li>
    <li><a href="${pageContext.request.contextPath}/roleList">角色管理</a></li>
    <li><a href="${pageContext.request.contextPath}/modifyAuthorityBefore?roleid=${roleid}">权限修改</a></li>
    </ul>
    </div>
    <c:forEach items="${mapForAuthority}" var="entry">    
	   <span id="${entry.key}" style="display: none">${entry.value}</span>
	</c:forEach> 
    <div class="formbody">
    <ul class="forminfo">
    <form action="${pageContext.request.contextPath}/updateRoleAuthority" method="post" id="form1" style="padding-bottom: 15px;">
    	<input type="text" value="${roleid}" name="roleid" style="display:none" id="roleid"/>
    	<input type="text" value="" name="actionids" id="actionids" style="display:none"/>
    	<div class="formtitle"><span>员工信息管理</span></div>
	    <li><input type="checkbox" value="1" name="action_id" >角色管理</input>
	    </li>
	    <li><input type="checkbox" value="2" name="action_id">账号管理</input>
	    </li>
	    <div class="formtitle"><span>客户及供应商管理</span></div>
	    <li><input type="checkbox" value="3" name="action_id">供应商管理</input>
	    </li>
	    <li><input type="checkbox" value="4" name="action_id">客户管理</input>
	    </li>
	    <li><input type="checkbox" value="11" name="action_id">客户分析</input>
	    </li>
	    <li><input type="checkbox" value="13" name="action_id">供应商分析</input>
	    </li>
	    <div class="formtitle"><span>商品信息管理</span></div>
	    <li><input type="checkbox" value="5" name="action_id">商品计量单位管理</input>
	    </li>
	    <li><input type="checkbox" value="6" name="action_id">商品类别管理</input>
	    </li>
	    <li><input type="checkbox" value="7" name="action_id">商品管理</input>
	    </li>
	   
	     <div class="formtitle"><span>库存信息管理</span></div>
	      <li><input type="checkbox" value="8" name="action_id">入出库管理</input>
	    </li>
	 	<li><input type="checkbox" value="9" name="action_id">库存查询</input>
	    </li>
	 	<li><input type="checkbox" value="10" name="action_id">库存分析</input>
	    </li>
    </form>
    <li><label>&nbsp;</label><input name="" type="button" class="btn" value="提交保存" onclick="submitData()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="" type="button" class="btn" value="取消" onclick="back('${pageContext.request.contextPath}/roleList')"/></li>
     <li>
    	<c:if test="${message !='' }">
    		<font color="red">${message}</font>
    	</c:if>
    </li>
    </ul>
    
    
    </div>


</body>

</html>
