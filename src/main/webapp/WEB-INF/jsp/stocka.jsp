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
<script language="JavaScript" src="${pageContext.request.contextPath}/static/js/echarts.min.js"></script>
<script type="text/javascript">
</script>
</head>
<body>
	<div id="main" style="width: 800px;height:600px;"></div>
	<script type="text/javascript">
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

        // 指定图表的配置项和数据
        var option = {
            title: {
                text: '库存分析'
            },
            tooltip : {
                trigger: 'axis',
                //show: true,   //default true
                showDelay: 0,
                hideDelay: 50,
                transitionDuration:0,
                backgroundColor : 'rgba(255,0,255,0.7)',
                borderColor : '#f50',
                borderRadius : 8,
                borderWidth: 2,
                padding: 10,    // [5, 10, 15, 20]
                position : function(p) {
                    return [p[0] + 10, p[1] - 10];
                },
                textStyle : {
                    color: 'yellow',
                    decoration: 'none',
                    fontFamily: 'Verdana, sans-serif',
                    fontSize: 15,
                    fontStyle: 'italic',
                    fontWeight: 'bold'
                },
                formatter: function (params,ticket,callback) {
                	debugger;
                    var res = "";
                    $.ajax({
            			url : '${pageContext.request.contextPath}/categoryAnalyz',
            			type : 'post',
            			data : 'name='+ params[0].name,
            			async : false, //默认为true 异步   
            			success : function(data) {
            				debugger;
            				res = data.message;
            			}
            		});
                    return res;
                }
            },
            legend: {
                data:['库存数量']
            },
            xAxis: {
                data: []
            },
            yAxis: {},
            series: [{
                name: '库存数量',
                type: 'bar',
                data: []
            }]
        };
        myChart.setOption(option);
        $.ajax({
			url : '${pageContext.request.contextPath}/stockaAnalyz',
			type : 'post',
			async : false, //默认为true 异步   
			success : function(data) {
				debugger;
				 myChart.setOption({
		                xAxis: {
		                    data: data.xAxis
		                },
		                series: [{
		                    // 根据名字对应到相应的系列
		                    name: '库存数量',
		                    data: data.series
		                }]
		            });
			}
		});
    </script>
</body>
</html>