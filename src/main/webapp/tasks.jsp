<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%
  String ctx = request.getContextPath();
%>

<div class="topbar">
  <h1>Tasks</h1>
  <a class="btn btn-primary" href="<%= ctx %>/tasks?action=new">+ New Task</a>
</div>

<!-- Optional flash message -->
<c:if test="${not empty message}">
  <p style="background:#e8f5e9; border:1px solid #c8e6c9; padding:10px; border-radius:6px;">
    ${message}
  </p>
</c:if>

<c:choose>
  <c:when test="${empty tasks}">
    <p>No tasks found.</p>
  </c:when>
  <c:otherwise>
    <table class="table table-striped"">
      <thead>
        <tr>
          <th">ID</th>
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
            <td><c:out value="${t.title}"/></td>
            <td><c:out value="${t.description}"/></td>
            <td>${t.status}</td>
            <td>${t.userId}</td>
            <td class="actions">
              <a class="btn btn-warning" href="<%= ctx %>/tasks?action=edit&id=${t.id}">Edit</a>
              <a class="btn btn-danger" href="<%= ctx %>/tasks?action=delete&id=${t.id}"
                 onclick="return confirm('Delete task #${t.id}?')">Delete</a>
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </c:otherwise>
</c:choose>


