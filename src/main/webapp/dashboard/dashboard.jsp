<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%
String ctx = request.getContextPath();
%>



<div class="mt-2">
	<!-- Optional flash message -->
	<c:if test="${not empty sessionScope.flashOk}">
		<div class="alert alert-success">${sessionScope.flashOk}</div>
		<c:remove var="flashOk" scope="session" />
	</c:if>

	<c:if test="${not empty sessionScope.flashErr}">
		<div class="alert alert-danger">${sessionScope.flashErr}</div>
		<c:remove var="flashErr" scope="session" />
	</c:if>
</div>
<div class="mb-4 mt-4">
	<h2>Dashboard ${taskStatusList.size()}</h2>
</div>
<div class="row">
	<div class="col-6">
		<div id="barChart" style="width: 100%; height: 400px;"></div>

	</div>
	<div class="col-6">
		<div id="pieChart" style="width: 100%; height: 400px;"></div>

	</div>
</div>

<script type="text/javascript">

	var totalTasks=[
		
	  <c:forEach var="s" items="${taskStatusList}" varStatus="loop">
        ${s.getTotal()}<c:if test="${!loop.last}">,</c:if>
      </c:forEach>
	];
	
	var statusName=[
		
		  <c:forEach var="s" items="${taskStatusList}" varStatus="loop">
	        "${s.getStatus()}"<c:if test="${!loop.last}">,</c:if>
	      </c:forEach>
		];
	var optionsBar = {
		chart : {
			type : 'bar'
		},
		series : [ {
			name : 'Tasks',
			data : totalTasks
		} ],
		xaxis : {
			categories : statusName
		},
		title : {
			text : "Task by status",
			align : "center",
			style : {
				fontSize : "20px",
				fontWeight : "bold",
				color : "#333"
			}
		},
		legend : {
			position : 'right', // top, bottom, left, right
			fontSize : '16px',
			markers : {
				width : 12,
				height : 12
			}
		}
	}

	
	
	
	var totalNotes=[
		
		  <c:forEach var="s" items="${statsNote}" varStatus="loop">
	        ${s.getTotal()}<c:if test="${!loop.last}">,</c:if>
	      </c:forEach>
		];
		
		var categoryList=[
			
			  <c:forEach var="s" items="${statsNote}" varStatus="loop">
		        "${s.getCategory()}"<c:if test="${!loop.last}">,</c:if>
		      </c:forEach>
			];
	var optionsPie = {
		chart : {
			type : 'pie',
			height : 400
		},
		series : totalNotes,
		labels : categoryList,
		title : {
			text : "Note by category",
			align : "center",
			style : {
				fontSize : "20px",
				fontWeight : "bold",
				color : "#333"
			}
		},
		legend : {
			position : 'right', // top, bottom, left, right
			fontSize : '16px',
			markers : {
				width : 12,
				height : 12
			}
		}
	};

	var barChart = new ApexCharts(document.querySelector("#barChart"),
			optionsBar);
	var pieChart = new ApexCharts(document.querySelector("#pieChart"),
			optionsPie);

	barChart.render();
	pieChart.render()
</script>



