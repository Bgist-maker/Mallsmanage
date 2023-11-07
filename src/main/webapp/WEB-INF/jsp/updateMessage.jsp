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
<script language="JavaScript" src="${pageContext.request.contextPath}/static/js/wangEditor.min.js"></script>
<script type="text/javascript">
	var E = "";
	var editor="";
	function submitData(){
		var text = editor.txt.text();
		if(text){
			$("#message").val(text);
		}
		$("#form1").submit();
	}
	function back(url){
		window.location.href=url;
	}
	$(function(){
		 E = window.wangEditor
	     editor = new E('#div')
	     editor.create()
	     editor.txt.html($("#message").val())
	})
</script>
</head>

<body>
	<div class="place">
    <span>位置</span>
    <ul class="placeul">
   <li><a href="${pageContext.request.contextPath}/index">首页</a></li>
      <li><a href="${pageContext.request.contextPath}/messageList?userid=${tempMessage.userid}&buyid=${tempMessage.buyid}&sellerid=${tempMessage.sellerid}&isdeal=${tempMessage.isdeal}">记录管理</a></li>
    <li><a href="${pageContext.request.contextPath}/updateMessageBefore?id=${tempMessage.id}">更新记录</a></li>
    </ul>
    </div>
    
    <div class="formbody">
    
    <div class="formtitle"><span>更新记录</span></div>
    
    <ul class="forminfo">
    <form action="${pageContext.request.contextPath}/updateMessage" method="post" id="form1">
   	 	<input name="id" type="text" class="dfinput" value="${tempMessage.id}" style="display: none"/>
   	 	<input type="text" value="${tempMessage.userid}" style="display: none" name="userid"/>
		<input type="text" value="${tempMessage.buyid}" style="display: none" name="buyid"/>
		<input type="text" value="${tempMessage.sellerid}" style="display: none" name="sellerid"/>
		<input type="text" value="${tempMessage.isdeal}" style="display: none" name="isdeal"/>
	    <input name="message"  id="message" type="text" class="dfinput" style="display: none" value="${tempMessage.message}"/>
	    <li><label>联系记录</label></li>
	    <li><div id="div"></div></li>
    </form>
    <li><label>&nbsp;</label><input name="" type="button" class="btn" value="提交保存" onclick="submitData()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="" type="button" class="btn" value="取消" onclick="back('${pageContext.request.contextPath}/messageList?userid=${tempMessage.userid}&buyid=${tempMessage.buyid}&sellerid=${tempMessage.sellerid}&isdeal=${tempMessage.isdeal}')"/></li>
     <li>
    	<c:if test="${message !='' }">
    		<font color="red">${message}</font>
    	</c:if>
    </li>
    </ul>
    
    
    </div>


</body>

</html>
