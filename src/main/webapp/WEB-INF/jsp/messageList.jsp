<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-W3CDTD XHTML 1.0 TransitionalEN" "http:www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http:www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${title}</title>
<link href="${pageContext.request.contextPath}/static/css/style.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/static/css/pagination.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="${pageContext.request.contextPath}/static/js/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/pagination.js"></script>

<script type="text/javascript">
 	$(document).ready(function(){
 		 $(".tiptop a").click(function(){
 		  	$(".tip").fadeOut(200);
 		 });
	
 	  	 $(".sure").click(function(){
 	        $.ajax({
 				url : "${pageContext.request.contextPath}/deleteMessage?id="+$("#saveId").val(),
 				type : 'post',
 				async : false, 
 				success : function(objs) {
 					if(objs.result==1){
 						window.location.href="${pageContext.request.contextPath}/messageList?userid="+$("#userid").val()
 						+"&buyid="+$("#buyid").val()
 						+"&sellerid="+$("#sellerid").val()
 						+"&isdeal="+$("#isdeal").val();
 					}else{
 						alert(objs.message);
 					}
 				}
 			});
 	        $(".tip").fadeOut(100);
 	     });
	
 		 $(".cancel").click(function(){
 		    $(".tip").fadeOut(100);
 		 });
 	 });
	
 	function modifyMessage(id) {
 		window.location.href="${pageContext.request.contextPath}/updateMessageBefore?id="+id;
 	}
	
 	function delMessage(id){
 		$(".tip").fadeIn(200);
 		$("#saveId").val(id);
 	}
	
 	function addMessage(){
 		window.location.href="${pageContext.request.contextPath}/addMessageBefore?userid="+$("#userid").val()
		+"&buyid="+$("#buyid").val()
		+"&sellerid="+$("#sellerid").val()
		+"&isdeal="+$("#isdeal").val()
 	}
 	
	function getPage(pageCount){
		window.location.href="${pageContext.request.contextPath}/messageList?userid="+$("#userid").val()
				+"&buyid="+$("#buyid").val()
				+"&sellerid="+$("#sellerid").val()
				+"&isdeal="+$("#isdeal").val()
				+"&pageCount="+pageCount
	}
	$(function(){
 		var onPagechange = function(page){
 			getPage(page);
		}
 		var taotal=$("#total").val();
 		var pageCount=$("#pageCount").val();
		var obj = {
		    wrapid:'page', //页面显示分页器容器id
		    total:taotal,//总条数
		    pagesize:10,//每页显示10条
		    currentPage:pageCount,//当前页
		    onPagechange:onPagechange,
		    //btnCount:7 页数过多时，显示省略号的边界页码按钮数量，可省略，且值是大于5的奇数
		}
		pagination.init(obj);
 	});
</script>
</head>
<body>
	<input type="text" value="${total}" style="display: none" id="total"/>
	<input type="text" value="${pageCount}" style="display: none" id="pageCount"/>
	<input type="text" value="${userid}" style="display: none" id="userid"/>
	<input type="text" value="${buyid}" style="display: none" id="buyid"/>
	<input type="text" value="${sellerid}" style="display: none" id="sellerid"/>
	<input type="text" value="${isdeal}" style="display: none" id="isdeal"/>
	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="${pageContext.request.contextPath}/index">首页</a></li>
    <li><a href="${pageContext.request.contextPath}/messageList?userid=${userid}&buyid=${buyid}&sellerid=${sellerid}&isdeal=${isdeal}">记录管理</a></li>
    </ul>
    </div>
    
    <div class="rightinfo">
    
    <div class="tools">
    
    	<ul class="toolbar">
        <c:if test="${isdeal==1}"><li onclick="addMessage()"><span ><img src="${pageContext.request.contextPath}/static/images/t01.png" /></span>添加</li></c:if>
        </ul>
    
    </div>
    
    
    <table class="tablelist">
    	<thead>
    	<tr>
        <th>编号</th>
        <th>员工工号</th>
        <th>信息内容</th>
        <th>对象</th>
        <th>供应商</th>
        <th>客户</th>
        <th>操作</th>
        </tr>
        </thead>
        
        <c:forEach items="${messages }" var="message">
        	<tbody>
        	 <tr>
		        <td>${message.id}</td>
		        <td>${message.user.userno}</td>
		        <td>${message.message }</td>
		        <td>
		        	<c:choose>
		        		<c:when test="${message.objecttype==0 }">
		        			客户
		        		</c:when>
		        		<c:otherwise>供应商</c:otherwise>
		        	</c:choose>
		        </td>
		         <td>
		        	<c:choose>
		        		<c:when test="${message.objecttype==1 }">
		        			${message.buyer.name }
		        		</c:when>
		        		<c:otherwise>无</c:otherwise>
		        	</c:choose>
		        </td>
		         <td>
		        	<c:choose>
		        		<c:when test="${message.objecttype==0 }">
		        			${message.seller.username }
		        		</c:when>
		        		<c:otherwise>无</c:otherwise>
		        	</c:choose>
		        </td>
		        <td><c:if test="${message.isdeal==1}"><a href="javaScript:void(0)" onclick="modifyMessage('${message.id}')" class="tablelink">修改</a></c:if> <a href="javaScript:void(0)" class="tablelink" onclick="delMessage('${message.id}')"> 删除</a></td>
	        </tr> 
	        </tbody>
        </c:forEach>
       
    </table>
    <div class="pagin">
    	<div class="message">共<i class="blue">${total }</i>条记录，当前显示第&nbsp;<i class="blue">${pageCount }&nbsp;</i>页</div>
	    <div id="page" class="paginList">
	    	
	    </div>
    </div>
    <div class="tip">
    	<div class="tiptop"><span>提示信息</span><a></a></div>
      <input type="text" id="saveId" style="display: none"></input>
      <div class="tipinfo">
        <span><img src="${pageContext.request.contextPath}/static/images/ticon.png" /></span>
        <div class="tipright">
        <p>是否确认对信息的删除?</p>
        <cite>如果是请点击确定按钮 ，否则请点取消。</cite>
        </div>
        </div>
        
        <div class="tipbtn">
        <input name="" type="button"  class="sure" value="确定" />&nbsp;
        <input name="" type="button"  class="cancel" value="取消" />
        </div>
    
    </div>
    
    
    
    
    </div>
    
    <script type="text/javascript">
	$('.tablelist tbody tr:odd').addClass('odd');
	</script>

</body>

</html>
