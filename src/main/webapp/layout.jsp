<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TaskNote App</title>
<link href="assets/css/bootstrap.min.css" rel="stylesheet" />

</head>
<body>
<%@ include file="partials/header.jsp" %>
<div class="container">
  
  <jsp:include page="${contentPage}" />
</div>

<script src="assets/js/bootstrap.bundle.min.js"></script>
</body>
</html>