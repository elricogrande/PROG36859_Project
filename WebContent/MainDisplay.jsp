<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Library Home</title>
</head>
<body>
	<%
		if ((Boolean) session.getAttribute("isClient")) {
	%>
	<ol class="list">
		<li><a href="client/Search">Search Library Resources</a></li>
		<li><a href="client/Loans">View Loan History</a></li>
	</ol>
	<%
		}
	%>
	<!-- INSERT HTML HERE -->
	<%
		if ((Boolean) session.getAttribute("isLibrarian")) {
	%>
	<ol class="list">
		<li><a href="librarian/LoanManagement">Search Library Resources</a></li>
	</ol>
	<%
		}
	%>
	<!-- INSERT HTML HERE  -->
	<%
		if ((Boolean) session.getAttribute("isAdmin")) {
	%>
	<ol class="list">
		<li><a href="admin/UserManagement">Search Library Resources</a></li>
		<li><a href="admin/ResourceManagement">View Loan History</a></li>
	</ol>
	<%
		}
	%>
	<!-- INSERT HTML HERE -->
	<ol class="list">
		<li><a href="ProfileManagement">Search Library Resources</a></li>
	</ol>

</body>
</html>