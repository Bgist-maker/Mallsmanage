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
	$(function(){
		$.ajax({
			url : '${pageContext.request.contextPath}/getAllCommoditys',
			type : 'post',
			async : false, //默认为true 异步   
			success : function(objs) {
				$("#commodityid").empty();
				for(var i =0;i<objs.length;i++) {
					$("#commodityid").append("<option value='"+objs[i].id+"'>"+objs[i].name+"</option>");
				}
				$("#commodityid").val($("#tempCommodityid").val())
				$("#operatetype").val($("#tempOperatetype").val())
			}
		});
		
		var isimportant = $("#tempOperatetype").val();
		if(isimportant ==1){
			$("#seller").show();
			$.ajax({
				url : '${pageContext.request.contextPath}/getAllSellers',
				type : 'post',
				async : false, //默认为true 异步   
				success : function(objs) {
					for(var i =0;i<objs.length;i++) {
						$("#sellerid").append("<option value='"+objs[i].id+"'>"+objs[i].username+"("+objs[i].company+")</option>");
					}
					$("#sellerid").val($("#tempSellerid").val())
				}
			});
		}else{
			$("#seller").hide();
		}
	})
	function change(){
		var isimportant = $("#operatetype").val();
		if(isimportant ==1){
			$("#seller").show();
			$.ajax({
				url : '${pageContext.request.contextPath}/getAllSellers',
				type : 'post',
				async : false, //默认为true 异步   
				success : function(objs) {
					for(var i =0;i<objs.length;i++) {
						$("#sellerid").append("<option value='"+objs[i].id+"'>"+objs[i].username+"("+objs[i].company+")</option>");
					}
				}
			});
		}else{
			$("#seller").hide();
			$("#sellerid").val("");
		}
	}
</script>
</head>

<body>
	<input type="text" id="tempCommodityid" style="display: none" value="${tempOperation.commodityid }"/>
	<input type="text" id="tempOperatetype" style="display: none" value="${tempOperation.operatetype }"/>
	<input type="text" id="tempSellerid" style="display: none" value="${tempOperation.sellerid }"/>
	<div class="place">
    <span>位置</span>
    <ul class="placeul">
    <li><a href="${pageContext.request.contextPath}/index">首页</a></li>
 	<li><a href="${pageContext.request.contextPath}/operationList">出入库管理</a></li>
    <li><a href="${pageContext.request.contextPath}/updateOperationBefore?id=${tempOperation.id}">出入库操作</a></li>
    </ul>
    </div>
    
    <div class="formbody">
    
    <div class="formtitle"><span>出入库操作</span></div>
    
    <ul class="forminfo">
    <form action="${pageContext.request.contextPath}/updateOperation" method="post" id="form1">
    	<input type="text" name="id" style="display: none" value="${tempOperation.id}"/>
   		 <li>
	    	<label>商品</label>
    		<select id="commodityid" name="commodityid">
			</select>
	    </li>
	    <li><label>数量</label><input name="amount" type="text" class="dfinput" value="${tempOperation.amount}"/><font style="color: red">&nbsp;&nbsp;&nbsp;*</font>
	    </li>
	     <li>
	    	<label>类型</label>
    		<select id="operatetype" name="operatetype" onchange="change()">
    			<option value="0">入库</option>
    			<option value="1">出库</option>
			</select>
	    </li>
	      <li id="seller" style="display: none">
	    	<label>客户</label>
    		<select id="sellerid" name="sellerid">
			</select>
	    </li>
	    <li><label>备注</label><input name="remark" type="text" class="dfinput"/><font style="color: red">&nbsp;&nbsp;&nbsp;*</font>
	    </li>
    </form>
    <li><label>&nbsp;</label><input name="" type="button" class="btn" value="提交保存" onclick="submitData()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="" type="button" class="btn" value="取消" onclick="back('${pageContext.request.contextPath}/operationList')"/></li>
     <li>
    	<c:if test="${message !='' }">
    		<font color="red">${message}</font>
    	</c:if>
    </li>
    </ul>
    
    
    </div>


</body>

</html>
