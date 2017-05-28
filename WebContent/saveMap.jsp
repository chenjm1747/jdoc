<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>ECharts 生成报表 保存图片 并插入到ftl中</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="<%=basePath %>js/echarts/echarts.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/jquery.min.js"></script>
  </head>
  
  <body>
    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div id="main" style="height:400px"></div>    
    <script type="text/javascript">

                var myChart = echarts.init(document.getElementById('main')); 
                
                option = {
        			    tooltip: {
        			        trigger: 'axis',
        			        axisPointer: {
        			            type: 'cross',
        			            crossStyle: {
        			                color: '#999'
        			            }
        			        }
        			    },
        			    toolbox: {
        			        feature: {
        			            dataView: {show: true, readOnly: false},
        			            magicType: {show: true, type: ['line', 'bar']},
        			            restore: {show: true},
        			            saveAsImage: {show: true}
        			        }
        			    },
        			    legend: {
        			        data:['%_PDF','%_CDF']
        			    },
        			    xAxis: [
        			        {
        			            type: 'category',
        			            axisLabel:{
        		                     interval:0,
        		                     rotate:90,
        		                     margin:2,
        		                     textStyle:{
        		                         color:"#222"
        		                     }
        		                 },
        			            data: ['[0.5)','[5.1)','[10.2)','[20.3)','[30.5)','[50.8)','[80.120)','[120.9999)'],
        			            axisPointer: {
        			                type: 'shadow'
        			            }
        			        }
        			    ],
        			    yAxis: [
        			        {
        			            type: 'value',
        			            name: '',
        			            min: 0,
        			            max: 100,
        			            interval: 20,
        			            axisLabel: {
        			                formatter: '{value} .00%'
        			            }
        			        },
        			        {
        			            type: 'value',
        			            name: '',
        			            min: 0,
        			            max: 50,
        			            interval: 10,
        			            axisLabel: {
        			                formatter: '{value} .00%'
        			            }
        			        }
        			    ],
        			    series: [
        			        {
        			            name:'',
        			            type:'bar',
        			            data:[0, 1, 3, 5, 28, 78, 86, 0.8]
        			        },
        			        {
        			            name:'',
        			            type:'line',
        			            yAxisIndex: 1,
        			            data:[0, 0.5, 1.5, 2.5, 9.8, 28, 50, 50]
        			        }
        			    ]
        			};

        
                // 为echarts对象加载数据 
                if (option && typeof option === "object") {
       			    myChart.setOption(option, true);
       			} 
                setTimeout(exportImage, 2000);
                function exportImage(){
	                var data = "a="+encodeURIComponent(myChart.getDataURL("png"));
	                console.log(data);
				    var xmlhttp;
				    if (window.XMLHttpRequest) { // code for IE7+, Firefox, Chrome, Opera, Safari
				        xmlhttp = new XMLHttpRequest();
				    } else { // code for IE6, IE5
				        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
				    }
				    xmlhttp.open("POST","<%=path%>/servlet/saveImage",true);
				    xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
				    xmlhttp.onreadystatechange = function() {
				        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				            alert("保存chart成功");
				        }
				    }
				    xmlhttp.send(data);
			    }
                
                function exportMap(){
	                var data = "a="+encodeURIComponent("http://115.159.218.200:4000/basics/map.html");
	                console.log(data);
				    var xmlhttp;
				    if (window.XMLHttpRequest) { // code for IE7+, Firefox, Chrome, Opera, Safari
				        xmlhttp = new XMLHttpRequest();
				    } else { // code for IE6, IE5
				        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
				    }
				    var map_url="<%=path%>/servlet/saveMap";
				    console.log("map_url is "+map_url);
				    xmlhttp.open("POST","<%=path%>/servlet/saveMap",true);
				    xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
				    xmlhttp.onreadystatechange = function() {
				        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				            alert("保存map成功");
				        }
				    }
				    xmlhttp.send(data);
			    }
			    
    </script>
</body>
</html>
