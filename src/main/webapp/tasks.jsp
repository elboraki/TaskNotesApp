<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

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

	<form class="row g-3 mb-3" method="get" action="<%= ctx %>/tasks">
		<div class="col-mx-auto">
			<input type="text" name="search" class="form-control"
				placeholder="Search tasks" value="${fn:escapeXml(search)}">
		</div>
		<div class="col-auto">
			<button type="submit" class="btn btn-primary mb-3">Search</button>
		</div>
	</form>
</div>
<c:choose>
	<c:when test="${empty tasks}">
		<p>No tasks found.</p>
	</c:when>
	<c:otherwise>
		<table class="table table-striped"">
			<thead>
				<tr>
					<th>ID</th>
					<th>Title</th>
					<th>Description</th>
					<th>Status</th>
					<th>User</th>
					<th>Actions</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="t" items="${tasks}">
					<tr>
						<td>${t.id}</td>
						<td><c:out value="${t.title}" /></td>
						<td><c:out value="${t.description}" /></td>
						<td>${t.status}</td>
						<td>${t.userId}</td>
						<td class="actions"><a class="btn btn-warning"
							href="<%= ctx %>/tasks?action=edit&id=${t.id}">Edit</a> <a
							class="btn btn-danger"
							href="<%= ctx %>/tasks?action=delete&id=${t.id}"
							onclick="return confirm('Delete task #${t.id}?')">Delete</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<nav aria-label="Page navigation example">
			<ul class="pagination">
				<%--For displaying Previous link except for the 1st page --%>
				<c:if test="${currentPage != 1}">
					<li class="page-item"><a class="page-link"
						href="<%= ctx %>/tasks?page=${currentPage - 1}">Previous</a></li>
				</c:if>
				<c:forEach begin="1" end="${noOfPages}" var="i">
					<c:choose>

						<c:when test="${ currentPage eq i}">

							<li class="page-item active"><a class="page-link"
								href="<%= ctx %>/tasks?page=${i}">${i}</a></li>

						</c:when>
						<c:otherwise>
							<li class="page-item"><a class="page-link"
								href="<%= ctx %>/tasks?page=${i}">${i}</a></li>
						</c:otherwise>
					</c:choose>


				</c:forEach>


				<%--For displaying Previous link except for the 1st page --%>
				<c:if test="${currentPage lt noOfPages}">
					<li class="page-item"><a class="page-link"
						href="<%= ctx %>/tasks?page=${currentPage + 1}">Next</a></li>
				</c:if>
			</ul>
		</nav>
	</c:otherwise>
</c:choose>


