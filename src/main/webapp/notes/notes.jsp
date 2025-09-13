<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%
String ctx = request.getContextPath();
%>

<div class="topbar">
	<h1>Notes</h1>
	<a class="btn btn-primary" href="<%=ctx%>/notes?action=new">+ New
		Note</a>
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

	<form class="row g-3 mb-3" method="get" action="<%=ctx%>/notes">
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
	<c:when test="${empty notes}">
		<p>No notes found.</p>
	</c:when>
	<c:otherwise>
		<div class="row">
			<c:forEach var="note" items="${notes}">
				<div class="col-sm-3">
					<div class="card">
						<div class="card-body">
							<h5 class="card-title">${note.category.name}</h5>
							<p class="card-text">${note.body}}</p>
							<a href="#" class="btn btn-primary">+</a> <a
								href="<%= ctx %>/notes?action=delete&id=${note.id}"
								onclick="return confirm('Delete task #${note.id}?')"
								class="btn btn-danger">-</a> <a
								href="<%= ctx %>/notes?action=edit&id=${note.id}"
								class="btn btn-warning">E</a>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>


		<nav aria-label="Page navigation example" class="mt-2">
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
								href="<%= ctx %>/notes?page=${i}">${i}</a></li>

						</c:when>
						<c:otherwise>
							<li class="page-item"><a class="page-link"
								href="<%= ctx %>/notes?page=${i}">${i}</a></li>
						</c:otherwise>
					</c:choose>


				</c:forEach>


				<%--For displaying Previous link except for the 1st page --%>
				<c:if test="${currentPage lt noOfPages}">
					<li class="page-item"><a class="page-link"
						href="<%= ctx %>/notes?page=${currentPage + 1}">Next</a></li>
				</c:if>
			</ul>
		</nav>
	</c:otherwise>
</c:choose>


