<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%
String ctx = request.getContextPath();
%>



<!-- Optional flash message -->
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

<div class="container mt-4">
	<form class="col g-3 mb-3" action="<%=ctx%>/login" method="post">

		<div class="col-4">
			<label class="form-label">Login:</label> <input class="form-control"
				name="username" type="text" />
		</div>
		<div class="col-4 mt-2"">
			<label class="form-label">Password:</label> <input
				class="form-control" name="password" type="password" />
		</div>
</div>
<div class="col-auto mt-2 mx-2">
	<button class="btn btn-primary">login</button>
</div>
</form>

</div>