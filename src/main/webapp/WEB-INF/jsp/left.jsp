<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="${pageContext.request.contextPath}/static/css/style.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="${pageContext.request.contextPath}/static/js/jquery.js"></script>
<script type="text/javascript">
	$(function(){	
		//导航切换
		$(".menuson li").click(function(){
			$(".menuson li.active").removeClass("active")
			$(this).addClass("active");
		});
		
		$('.title').click(function(){
			var $ul = $(this).next('ul');
			$('dd').find('ul').slideUp();
			if($ul.is(':visible')){
				$(this).next('ul').slideUp();
			}else{
				$(this).next('ul').slideDown();
			}
		});
	})	
	$(function(){
		//导航栏确定显示
		debugger;
		var actionids = "";
		var roleid = $("#roleid").val();
		$("li").each(function(){
			if($("#"+$(this).attr("value")).text() != roleid){
				$(this).hide();
				actionids = actionids + $(this).attr("value");
			}
		});
		if(actionids.indexOf("12") != -1){
			$("#12").hide();
		}
		if(actionids.indexOf("341113") != -1){
			$("#341113").hide();
		}
		if(actionids.indexOf("567") != -1){
			$("#567").hide();
		}
		if(actionids.indexOf("8910") != -1){
			$("#8910").hide();
		}
	});
</script>
</head>
<body style="background:#f0f9fd;">
	<input type="text" value="${user.roleid}" id="roleid" style="display: none">
	<c:forEach items="${authoritys}" var="authority">
		<span id="${authority.actionid }" style="display: none">${authority.roleid }</span>
	</c:forEach>
	<div class="lefttop"><span></span>导航</div>
    
    <dl class="leftmenu">
        
    <dd>
    <div class="title" id="12">
    <span ><img src="${pageContext.request.contextPath}/static/images/leftico01.png" /></span>员工信息管理
    </div>
    	<ul class="menuson">         
          <li class="active" value="1"><cite></cite><a href="${pageContext.request.contextPath}/roleList" target="rightFrame">角色管理</a><i></i></li> 
          <li class="active" value="2"><cite></cite><a href="${pageContext.request.contextPath}/userList" target="rightFrame">账号管理</a><i></i></li>           
        </ul>    
    </dd>
    <dd>
    <div class="title" id="341113">
    <span><img src="${pageContext.request.contextPath}/static/images/leftico01.png" /></span>客户及供应商管理</div>
    	<ul class="menuson">
        <li value="3"><cite></cite><a href="${pageContext.request.contextPath}/buyerList" target="rightFrame">供应商管理</a><i></i></li>       
        <li value="4"><cite></cite><a href="${pageContext.request.contextPath}/sellerList" target="rightFrame">客户管理</a><i></i></li>
        <li value="11"><cite></cite><a href="${pageContext.request.contextPath}/selleranalysis" target="rightFrame">客户分析</a><i></i></li>
        <li value="13"><cite></cite><a href="${pageContext.request.contextPath}/buyeranalysis" target="rightFrame">供应商分析</a><i></i></li>
        </ul>
    </dd>
    <dd>
    <div class="title" id="567">
    <span><img src="${pageContext.request.contextPath}/static/images/leftico01.png" /></span>商品信息管理
    </div>
    	<ul class="menuson">
    	  <li class="active" value="5"><cite></cite><a href="${pageContext.request.contextPath}/unitList" target="rightFrame">商品计量单位管理</a><i></i></li>
          <li class="active" value="6"><cite></cite><a href="${pageContext.request.contextPath}/categoryList" target="rightFrame">商品类别管理</a><i></i></li>
          <li class="active" value="7"><cite></cite><a href="${pageContext.request.contextPath}/commodityList" target="rightFrame">商品管理</a><i></i></li>            
        </ul>    
    </dd>
  <!--   <dd>
   <div class="title" id="88">
    <span><img src="${pageContext.request.contextPath}/static/images/leftico01.png" /></span>出入库管理
    </div>
    	<ul class="menuson">
          <li class="active" value="8"><cite></cite><a href="${pageContext.request.contextPath}/operationList" target="rightFrame">出入库管理</a><i></i></li>
        </ul>    
    </dd>
    -->
    <dd>
    <div class="title" id="8910">
    <span><img src="${pageContext.request.contextPath}/static/images/leftico01.png" /></span>库存及出入库管理
    </div>
    	<ul class="menuson">
    	 <li class="active" value="8"><cite></cite><a href="${pageContext.request.contextPath}/operationList" target="rightFrame">出入库管理</a><i></i></li>
          <li class="active" value="9"><cite></cite><a href="${pageContext.request.contextPath}/commodityListForSearch" target="rightFrame">库存查询</a><i></i></li>
          <li class="active" value="10"><cite></cite><a href="${pageContext.request.contextPath}/stocka" target="rightFrame">库存分析</a><i></i></li>           
        </ul>    
    </dd>
    </dl>
</body>
</html>