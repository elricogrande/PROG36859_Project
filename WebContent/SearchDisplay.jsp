<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.ArrayList"
	import="ca.sheridan.data.Resource"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search Resources</title>
</head>
<body>
	<table border ="1" class="results">
	<tr><td>TITLE</td><td>AUTHOR(S)</td><td>TYPE</td><td>PUBLISHER</td><td>ISBN</td></tr>
		<%
			ArrayList<Resource> searchResults = (ArrayList<Resource>) request.getAttribute("searchResults");
			for (int i = 0; i < searchResults.size(); i++) {
				%>
				<tr>
				<td><%=searchResults.get(i).getTitle() %></td>
				<td><ul class="innerList">
				<% for (int j = 0; j < searchResults.get(i).getAuthors().size(); j++) { %>
				<li>
				<%=searchResults.get(i).getAuthors().get(j).getLname() + ", " + searchResults.get(i).getAuthors().get(j).getFname()%>
				</li>
				<%} %>
				</ul></td>
				<td><%=searchResults.get(i).getRtype() %></td>
				<td><%=searchResults.get(i).getPublisher().getName() %></td>
				<td><%=searchResults.get(i).getIsbn() %></td>
				</tr>
				<%
				}
				%>
	</table>
</body>
</html>