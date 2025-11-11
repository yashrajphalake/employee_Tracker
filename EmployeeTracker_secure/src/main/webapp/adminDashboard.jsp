<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%
  Boolean isAdmin = (Boolean) session.getAttribute("isAdminLoggedIn");
  if (isAdmin == null || !isAdmin) {
      response.sendRedirect("login.jsp");
      return;
  }
%>
<!DOCTYPE html>
<html>
<head><title>Admin Dashboard</title></head>
<body>
  <h1>Welcome, Admin!</h1>
  <p>Logged in as: <b><%= session.getAttribute("adminUsername") %></b></p>
  <a href="logout">Logout</a>
</body>
</html>
