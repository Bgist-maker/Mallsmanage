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
	function getPage(pageCount){
		window.location.href="${pageContext.request.contextPath}/commodityListForSearch?pageCount="+pageCount
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
	function searchData(){
		var commodityName = $("#commodityName").val();
 		if(commodityName=='商品名'){
 			commodityName = '';
 		}
		var categoryName = $("#categoryName").val();
 		if(categoryName=='类别'){
 			categoryName = '';
 		}
		var min = $("#min").val();
 		if(min=='最小库存量'){
 			min = '';
 		}
		var max = $("#max").val();
 		if(max=='最大库存量'){
 			max = '';
 		}
		
 		window.location.href="${pageContext.request.contextPath}/commodityListForSearch?commodityName="+encodeURI(encodeURI(commodityName))+"&categoryName="+encodeURI(encodeURI(categoryName))
 		+"&min="+encodeURI(encodeURI(min))+"&max="+encodeURI(encodeURI(max));
	}
</script>
</head>
<body>
	<input type="text" value="${total}" style="display: none" id="total"/>
	<input type="text" value="${pageCount}" style="display: none" id="pageCount"/>
	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="${pageContext.request.contextPath}/index">首页</a></li>
    <li><a href="${pageContext.request.contextPath}/commodityListForSearch">库存查询</a></li>
    </ul>
    </div>
    
    <div class="rightinfo">
    
    <div class="tools">
     <ul class="seachform">
        <li>
            <input name="" type="text" class="scinput" value="${commodityName}" onfocus="if (value =='商品名'){value =''}"
    		onblur="if (value ==''){value='商品名'}" id="commodityName"/>
            <input name="" type="text" class="scinput" value="${categoryName}" onfocus="if (value =='类别'){value =''}"
    		onblur="if (value ==''){value='类别'}" id="categoryName"/>
          </li>
          <li>库存量：<input name="" type="text" class="scinput" value="${min}" onfocus="if (value =='最小库存量'){value =''}"
    		onblur="if (value ==''){value='最小库存量'}" id="min"/>-<input name="" type="text" class="scinput" value="${max}" onfocus="if (value =='最大库存量'){value =''}"
    		onblur="if (value ==''){value='最大库存量'}" id="max"/></li>
          <li><input name="" type="button" class="scbtn" value="查询" onclick="searchData()"/></li>
        </ul>
    </div>
    
    
    <table class="tablelist">
    	<thead>
    	<tr>
        <th>编号</th>
        <th>名字</th>
        <th>库存数量</th>
        <th>品牌</th>
        <th>计量单位</th>
        <th>所属类别</th>
        <th>供应商</th>
        </tr>
        </thead>
        
        <c:forEach items="${commoditys }" var="commodity">
        	<tbody>
        	 <tr>
		        <td>${commodity.id}</td>
		        <td>${commodity.name}</td>
		        <td>${commodity.stockamount }</td>
		        <td>${commodity.brand }</td>
		        <td>${commodity.unit.name }</td>
		        <td>${commodity.category.name }</td>
		        <td>${commodity.buyer.name }</td>
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
