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
	<h2>Edit a Task</h2>
	<form class="row g-3 mb-3" method="POST" action="<%=ctx%>/tasks">
	       <input type="hidden" name="_method" value="PUT">
		<div class="col-mx-auto">
			<input type="hidden" name="id" value="${task.getId()}"> <label
				class="form-label">Title</label> <input type="text" name="title"
				class="form-control" placeholder="Title" value="${task.getTitle()}">
		</div>
		<div class="mb-3">
			<label class="form-label">Description</label>
			<textarea class="form-control" name="description" rows="3">${task.getDescription()}</textarea>
		</div>
		<div class="mb-3">
			<label class="form-label">Status</label> <select class="form-select"
				name="status" aria-label="Default select status">
				<option value="Pending"
					<c:if test="${task.getStatus() == 'Pending'}">selected</c:if>>Pending</option>
				<option value="In Progress"
					<c:if test="${task.getStatus()== 'In Progress'}">selected</c:if>>In
					Progress</option>
				<option value="Completed"
					<c:if test="${task.getStatus() == 'Completed'}">selected</c:if>>Completed</option>
			</select>
		</div>

		<div class="col-auto">
			<button type="submit" class="btn btn-primary mb-3">Update</button>
		</div>
		<div class="col-auto">
			<a class="btn btn-warning" href="<%=ctx%>/tasks">Back</a>
		</div>
	</form>
</div>
