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
		$("#message").val(editor.txt.text());
		$("#form1").submit();
	}
	function back(url){
		window.location.href=url;
	}
	$(function(){
		 E = window.wangEditor
	     editor = new E('#div')
	     editor.create()
	})
</script>
</head>

<body>
	<input type="text" value="${userid}" style="display: none" id="userid"/>
	<input type="text" value="${buyid}" style="display: none" id="buyid"/>
	<input type="text" value="${sellerid}" style="display: none" id="sellerid"/>
	<input type="text" value="${isdeal}" style="display: none" id="isdeal"/>
	<div class="place">
    <span>位置</span>
    <ul class="placeul">
    <li><a href="${pageContext.request.contextPath}/index">首页</a></li>
      <li><a href="${pageContext.request.contextPath}/messageList?userid=${userid}&buyid=${buyid}&sellerid=${sellerid}&isdeal=${isdeal}">记录管理</a></li>
    <li><a href="${pageContext.request.contextPath}/addMessageBefore?userid=${userid}&buyid=${buyid}&sellerid=${sellerid}&isdeal=${isdeal}">添加记录</a></li>
    </ul>
    </div>
    
    <div class="formbody">
    
    <div class="formtitle"><span>添加记录</span></div>
    
    <ul class="forminfo">
    <form action="${pageContext.request.contextPath}/addMessage" method="post" id="form1">
	    <input name="message"  id="message" type="text" class="dfinput" style="display: none"/>
	    <input name="userid"  id="userid" type="text" value="${userid}" class="dfinput" style="display: none"/>
	    <input name="buyid"  id="buyid" type="text"  value="${ buyid}" class="dfinput" style="display: none"/>
	    <input name="sellerid"  id="sellerid" type="text" value="${ sellerid}" class="dfinput" style="display: none"/>
	    <input name="isdeal"  id="isdeal" type="text" value="${ isdeal}" class="dfinput" style="display: none"/>
	    <li><label>联系记录</label></li>
	    <li><div id="div"></div></li>
    </form>
    <li><label>&nbsp;</label><input name="" type="button" class="btn" value="提交保存" onclick="submitData()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="" type="button" class="btn" value="取消" onclick="back('${pageContext.request.contextPath}/messageList?userid=${userid}&buyid=${buyid}&sellerid=${sellerid}&isdeal=${isdeal}')"/></li>
     <li>
    	<c:if test="${message !='' }">
    		<font color="red">${message}</font>
    	</c:if>
    </li>
    </ul>
    
    
    </div>


</body>

</html>
