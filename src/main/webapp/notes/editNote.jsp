<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%
String ctx = request.getContextPath();
%>
<div class="topbar mt-2">
	<h1>Edit note</h1>

</div>
<!-- Optional flash message -->
<c:if test="${not empty message}">
	<p
		style="background: #e8f5e9; border: 1px solid #c8e6c9; padding: 10px; border-radius: 6px;">
		${message}</p>
</c:if>
<div class="container mt-4">

	<form class="row g-3 mb-3" method="post" action="<%=ctx%>/notes">
		<input type="hidden" name="action" value="edit"> 
		<div class="mb-3">
			<label class="form-label">body</label>
			<textarea class="form-control" name="body" rows="3">${note.getBody()}</textarea>
		</div>
		<div class="mb-3">


			<label class="form-label">Category</label> <select
				class="form-select" name="categorie"
				aria-label="Default select category">
				<option value="1"
					<c:if test="${note.getCategory().getId() == 1}">selected</c:if>>Work</option>
				<option value="2"
					<c:if test="${note.getCategory().getId() == 2}">selected</c:if>>Personal</option>
				<option value="3"
					<c:if test="${note.getCategory().getId() == 3}">selected</c:if>>Study</option>
				<option value="4"
					<c:if test="${note.getCategory().getId() == 4}">selected</c:if>>Travel</option>
				<option value="5"
					<c:if test="${note.getCategory().getId() == 5}">selected</c:if>>Finance</option>
			</select>
		</div>

		<div class="col-auto">
			<button type="submit" class="btn btn-primary mb-3">Update</button>
		</div>
	</form>
</div>
