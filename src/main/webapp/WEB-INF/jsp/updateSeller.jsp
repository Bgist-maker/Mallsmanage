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
	function change(){
		var isimportant = $("#isimportant").val();
		if(isimportant ==1){
			$("#user").show();
			$.ajax({
				url : '${pageContext.request.contextPath}/getAllUsers',
				type : 'post',
				async : false, //默认为true 异步   
				success : function(objs) {
					$("#userid").empty();
					$("#userid").append("<option value=''></option>");
					for(var i =0;i<objs.length;i++) {
						//分割线
						var name1=null;
					    $.ajax({url: '${pageContext.request.contextPath}/getAllRoles',type:'post',async:false,success: function(data2){	
						for(var k =0;k<data2.length;k++) {
						var j=objs[i].roleid;
						if(j==data2[k].id){
							name1=data2[k].name;
							//$("#userid").append("<option value='"+objs[i].id+"'>"+objs[i].userno+"("+data2[i].name+")("+objs[i].truename+")</option>");
							break;
						}
							}
						}
					     });
				
						$("#userid").append("<option value='"+objs[i].id+"'>"+objs[i].userno+"("+name1+")("+objs[i].truename+")</option>");

						//分割线
					//	$("#userid").append("<option value='"+objs[i].id+"'>"+objs[i].userno+"("+objs[i].truename+")</option>");
					}
				}
			});
			$("#userid").val($("tempUserid").val());
		}else{
			$("#user").hide();
		}
	}
	$(function(){
		var tempisImportant = $("#tempisImportant").val();
		$("#isimportant").val(tempisImportant);
		if(tempisImportant ==1){
			$("#user").show();
			$.ajax({
				url : '${pageContext.request.contextPath}/getAllUsers',
				type : 'post',
				async : false, //默认为true 异步   
				success : function(objs) {
					$("#userid").empty();
					$("#userid").append("<option value=''></option>");
					for(var i =0;i<objs.length;i++) {
						$("#userid").append("<option value='"+objs[i].id+"'>"+objs[i].userno+"("+objs[i].truename+")</option>");
					}
					var tempUserid =  $("#tempUserid").val()
					$("#userid").val(tempUserid);
				}
			});
			
		}else{
			$("#user").hide();
		}
	})
	$(function(){
		var birthday = new Date($("#tempisBirthday").val());
		$("#birthday").val(birthday.getFullYear() + '-' + (birthday.getMonth()>9?birthday.getMonth() + 1:"0"+(birthday.getMonth() + 1)) + '-' + (birthday.getDate()>10?birthday.getDate():"0"+birthday.getDate()));
	})
</script>
</head>

<body>
	<input type="text" value="${tempSeller.userid }" id="tempUserid" style="display: none">
	<input type="text" value="${tempSeller.isimportant }" id="tempisImportant" style="display: none">
	<input type="text" value="${tempSeller.birthday }" id="tempisBirthday" style="display: none">
	<div class="place">
    <span>位置</span>
    <ul class="placeul">
    <li><a href="${pageContext.request.contextPath}/index">首页</a></li>
    <li><a href="${pageContext.request.contextPath}/sellerList">客户管理</a></li>
    <li><a href="${pageContext.request.contextPath}/updateSellerBefore?id=${tempSeller.id}">更新客户</a></li>
    </ul>
    </div>
    
    <div class="formbody">
    
    <div class="formtitle"><span>更新客户</span></div>
    
    <ul class="forminfo">
    <form action="${pageContext.request.contextPath}/updateSeller" method="post" id="form1">
    	<input type="text" value="${tempSeller.id }"name="id" style="display: none">
	    <li><label>姓名</label><input name="username" type="text" class="dfinput" value="${tempSeller.username}"/><font style="color: red">&nbsp;&nbsp;&nbsp;*</font>
	    </li>
	    <li><label>单位名称</label><input name="company" type="text" class="dfinput" value="${tempSeller.company}"/><font style="color: red">&nbsp;&nbsp;&nbsp;*</font>
	    </li>
	    <li><label>生日</label><input name="birthday" type="date" class="dfinput" value="${tempSeller.birthday}" id="birthday"/><font style="color: red">&nbsp;&nbsp;&nbsp;*</font>
	    </li>
	    <li><label>联系电话</label><input name="phone" type="text" class="dfinput" value="${tempSeller.phone}"/><font style="color: red">&nbsp;&nbsp;&nbsp;*</font>
	    </li>
	    <li><label>邮箱</label><input name="email" type="text" class="dfinput" value="${tempSeller.email}"/><font style="color: red">&nbsp;&nbsp;&nbsp;*</font>
	    </li>
	   
<!--  	    <li><label>所在城市</label><input name="city" type="text" class="dfinput" value="${tempSeller.city}"/><font style="color: red">&nbsp;&nbsp;&nbsp;*</font>
	    </li>
-->	   
        <li><label>所在省份</label><select name="city"  /> 
	            <option value="浙江"> 浙江</option>
				<option value="山东"> 山东</option>
				<option value="江苏"> 江苏</option>
				<option value="广东">广东</option>
				<option value="新疆"> 新疆</option>
				<option value="上海"> 上海</option>
				<option value="北京"> 北京</option>
				<option value="河南"> 河南</option>
          </select>
	    
	    </li>
       
	    <li><label>详细地址</label><input name="address" type="text" class="dfinput" value="${tempSeller.address}"/><font style="color: red">&nbsp;&nbsp;&nbsp;*</font>
	    </li>
	    <li><label>备注</label><input name="remark" type="text" class="dfinput" value="${tempSeller.remark}"/><font style="color: red">&nbsp;&nbsp;&nbsp;*</font>
	    </li>
	     <li>
	    	<label>是否重要</label>
    		<select id="isimportant" name="isimportant" onchange="change()">
    			<option value="0">否</option>
    			<option value="1">是</option>
			</select>
	    </li>
	    <li id="user" style="display: none">
	    	<label>业务员工</label>
    		<select id="userid" name="userid">
			</select>
	    </li>
    </form>
    <li><label>&nbsp;</label><input name="" type="button" class="btn" value="提交保存" onclick="submitData()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="" type="button" class="btn" value="取消" onclick="back('${pageContext.request.contextPath}/sellerList')"/></li>
     <li>
    	<c:if test="${message !='' }">
    		<font color="red">${message}</font>
    	</c:if>
    </li>
    </ul>
    
    
    </div>


</body>

</html>
