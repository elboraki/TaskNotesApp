<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%
String ctx = request.getContextPath();
%>



<!-- Optional flash message -->
<c:if test="${not empty message}">
	<p
		style="background: #e8f5e9; border: 1px solid #c8e6c9; padding: 10px; border-radius: 6px;">
		${message}</p>
</c:if>

<div class="container mt-4">
	<form class="col g-3 mb-3" action="/login" method="post">

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