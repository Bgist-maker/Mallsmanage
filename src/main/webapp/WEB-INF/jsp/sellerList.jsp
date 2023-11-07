<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
 				url : "${pageContext.request.contextPath}/deleteSeller?id="+$("#saveId").val(),
 				type : 'post',
 				async : false, 
 				success : function(objs) {
 					if(objs.result==1){
 						window.location.href="${pageContext.request.contextPath}/sellerList";
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
	
 	function modifySeller(id) {
 		window.location.href="${pageContext.request.contextPath}/updateSellerBefore?id="+id;
 	}
	
 	function delSeller(id){
 		$(".tip").fadeIn(200);
 		$("#saveId").val(id);
 	}
	
 	function addSeller(){
 		window.location.href="${pageContext.request.contextPath}/addSellerBefore"
 	}
 	
	function getPage(pageCount){
		window.location.href="${pageContext.request.contextPath}/sellerList?pageCount="+pageCount
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
	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="${pageContext.request.contextPath}/index">首页</a></li>
    <li><a href="${pageContext.request.contextPath}/sellerList">客户管理</a></li>
    </ul>
    </div>
    
    <div class="rightinfo">
    
    <div class="tools">
    
    	<ul class="toolbar">
        <li onclick="addSeller()"><span ><img src="${pageContext.request.contextPath}/static/images/t01.png" /></span>添加</li>
        </ul>
    
    </div>
    
    
    <table class="tablelist">
    	<thead>
    	<tr>
        <th>编号</th>
        <th>姓名</th>
        <th>所在单位</th>
        <th>生日</th>
        <th>联系电话</th>
        <th>邮箱</th>
        <th>所在城市</th>
        <th>详细地址</th>
        <th>备注</th>
        <th>是否重要</th>
        <th>业务员工</th>
        <th>操作</th>
        </tr>
        </thead>
        
        <c:forEach items="${sellers }" var="seller">
        	<tbody>
        	 <tr>
		        <td>${seller.id}</td>
		        <td>${seller.username}</td>
		        <td>${seller.company }</td>
		        <td><fmt:formatDate value="${seller.birthday }" pattern="yyyy-MM-dd" /></td>
		        <td>${seller.phone }</td>
		        <td>${seller.email }</td>
		        <td>${seller.city}</td>
		        <td>${seller.address }</td>
		        <td>${seller.remark }</td>
		        <td>
		        	<c:choose>
		        		<c:when test="${seller.isimportant == 0 }">
		        			否
		        		</c:when>
		        		<c:otherwise>
		        			是
		        		</c:otherwise>
		        	</c:choose>
				</td>
				<td><c:if test="${seller.isimportant == 1 }">${seller.user.truename}</c:if></td>
		        <td><a href="javaScript:void(0)" onclick="modifySeller('${seller.id}')" class="tablelink">修改</a> <a href="javaScript:void(0)" class="tablelink" onclick="delSeller('${seller.id}')"> 删除</a>
		        <c:if test="${seller.userid != null &&seller.userid == user.id &&seller.isimportant == 1}"><a href="${pageContext.request.contextPath}/messageList?userid=${user.id }&sellerid=${seller.id }&isdeal=1" class="tablelink" > 联系记录管理</a></c:if>
		        </td>
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
