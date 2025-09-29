<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%
String ctx = request.getContextPath();
%>

<div class="topbar">
	<h1>Tasks</h1>
	<a class="btn btn-primary" href="<%=ctx%>/tasks?action=new"><i
		class="bi bi-plus-circle-fill"></i> New Task</a>
</div>

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

	<form class="row g-3 mb-3" method="get" action="<%=ctx%>/tasks">
		<div class="col-mx-auto">
			<input type="text" name="search" class="form-control"
				placeholder="Search tasks" value="${fn:escapeXml(search)}">
		</div>
		<div class="col-auto">
			<button type="submit" class="btn btn-primary mb-3">
				<i class="bi bi-search"></i> Search
			</button>
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
						<td class="actions"><a class="btn btn-warning"
							href="<%= ctx %>/tasks?action=edit&id=${t.id}"> <i
								class="bi bi-pencil-square"></i>
						</a> <input type="hidden" _method="delete" /> <a
							class="btn btn-danger"
							href="<%= ctx %>/tasks?action=delete&id=${t.id}"
							onclick="return confirm('Delete task #${t.id}?')"><i
								class="bi bi-trash-fill"></i></a></td>
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


