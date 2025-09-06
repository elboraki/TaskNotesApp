<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%
String ctx = request.getContextPath();
%>

<div class="topbar">
	<h1>Tasks</h1>
	<a class="btn btn-primary" href="<%=ctx%>/tasks?action=new">+ New
		Task</a>
</div>

<!-- Optional flash message -->
<c:if test="${not empty message}">
	<p
		style="background: #e8f5e9; border: 1px solid #c8e6c9; padding: 10px; border-radius: 6px;">
		${message}</p>
</c:if>
<div class="container mt-4">

	<form class="row g-3 mb-3" method="post" action="<%=ctx%>/tasks">
		<div class="col-mx-auto">
			<label class="form-label">Title</label> <input type="text"
				name="title" class="form-control" placeholder="Search tasks"
				value="${fn:escapeXml(search)}">
		</div>
		<div class="mb-3">
			<label class="form-label">Description</label>
			<textarea class="form-control" name="description" rows="3"></textarea>
		</div>
		<div class="mb-3">
			<label class="form-label">Status</label> <select class="form-select" name="status"
				aria-label="Default select status">
				<option value="Pending" selected="Pending">Pending</option>
				<option value="In Progress">In Progress</option>
				<option value="Completed">Completed</option>
			</select>
		</div>

		<div class="col-auto">
			<button type="submit" class="btn btn-primary mb-3">Add</button>
		</div>
	</form>
</div>
