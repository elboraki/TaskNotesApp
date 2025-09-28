<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%
String ctx = request.getContextPath();
%>
<nav class="navbar navbar-expand-lg bg-primary" data-bs-theme="dark">

	<div class="container">
		<a class="navbar-brand" href="#">TKNotes</a>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
			data-bs-target="#navbarColor01" aria-controls="navbarColor01"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarColor01">
			<ul class="navbar-nav me-auto">
							<li class="nav-item"><a class="nav-link active" href="dashboard">Dashboard
				</a></li>
				<li class="nav-item"><a class="nav-link active" href="tasks">Tasks
						<span class="visually-hidden">(current)</span>
				</a></li>
				<li class="nav-item"><a class="nav-link" href="notes">Notes</a>
				</li>
				<li class="nav-item"><a class="nav-link" href="#">About</a></li>

			</ul>
			<ul class="navbar-nav left">
				<c:if test="${not empty sessionScope.username}">
					<li class="nav-item"><a class="nav-link"
						href="<%=ctx%>/logout">Logout</a></li>
				</c:if>

			</ul>

		</div>
	</div>
</nav>