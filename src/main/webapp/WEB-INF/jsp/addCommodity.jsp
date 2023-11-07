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
	//从后台获取表单显示在选项界面里
	$(function(){
		$.ajax({
			url : '${pageContext.request.contextPath}/getAllUnits',
			type : 'post',//get 为在显示登录界面，post为提交表单。
			async : false, //默认为true 异步   
			success : function(objs) {
				for(var i =0;i<objs.length;i++) {
					$("#unitid").append("<option value='"+objs[i].id+"'>"+objs[i].name+"</option>");
				}
			}
		});
		$.ajax({
			url : '${pageContext.request.contextPath}/getAllCategorys',
			type : 'post',
			async : false, //默认为true 异步   
			success : function(objs) {//成功之后的处理
				for(var i =0;i<objs.length;i++) {
					$("#categoryid").append("<option value='"+objs[i].id+"'>"+objs[i].name+"</option>");
				}
			}
		});
		$.ajax({
			url : '${pageContext.request.contextPath}/getAllBuyers',
			type : 'post',
			async : false, //默认为true 异步   
			success : function(objs) {
				for(var i =0;i<objs.length;i++) {
					$("#buyerid").append("<option value='"+objs[i].id+"'>"+objs[i].name+"</option>");
				}
			}
		});
	})
</script>
</head>

<body>

	<div class="place">
    <span>位置</span>
    <ul class="placeul">
    <li><a href="${pageContext.request.contextPath}/index">首页</a></li>
    <li><a href="${pageContext.request.contextPath}/commodityList">商品管理</a></li>
    <li><a href="${pageContext.request.contextPath}/addCommodityBefore">添加商品</a></li>
    </ul>
    </div>
    
    <div class="formbody">
    
    <div class="formtitle"><span>添加商品</span></div>
    
    <ul class="forminfo">
    <form action="${pageContext.request.contextPath}/addCommodity" method="post" id="form1">
	    <li><label>名字</label><input name=name type="text" class="dfinput"/><font style="color: red">&nbsp;&nbsp;&nbsp;*</font>
	    </li>
	    <li><label>库存数量</label><input name="stockamount" type="text" class="dfinput" /><font style="color: red">&nbsp;&nbsp;&nbsp;*</font>
	    </li>
	    <li><label>品牌</label><input name="brand" type="text" class="dfinput" /><font style="color: red">&nbsp;&nbsp;&nbsp;*</font>
	    </li>
	    <li><label>进价</label><input name="buy" type="text" class="dfinput" /><font style="color: red">&nbsp;&nbsp;&nbsp;*</font>
	    </li>
	    <li><label>售价</label><input name="sell" type="text" class="dfinput" /><font style="color: red">&nbsp;&nbsp;&nbsp;*</font>
	    </li>
	    <li>
	    	<label>计量单位</label>
    		<select id="unitid" name="unitid">
			</select>
	    </li>
	    <li>
	    	<label>商品类别</label>
    		<select id="categoryid" name="categoryid">
			</select>
	    </li>
	    <li>
	    	<label>供应商</label>
    		<select id="buyerid" name="buyerid">
			</select>
	    </li>
    </form>
    <li><label>&nbsp;</label><input name="" type="button" class="btn" value="提交保存" onclick="submitData()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="" type="button" class="btn" value="取消" onclick="back('${pageContext.request.contextPath}/commodityList')"/></li>
     <li>
    	<c:if test="${message !='' }">
    		<font color="red">${message}</font>
    	</c:if>
    </li>
    </ul>
    
    
    </div>


</body>

</html>
