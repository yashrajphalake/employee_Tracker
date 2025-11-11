<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<html>
<head>
<title>Login Page</title>
<style>
body { font-family: Arial; background: #f1f1f1; }
.card { width: 320px; margin: 20px auto; padding: 20px; background: white; border-radius: 8px; box-shadow: 0 0 5px gray; }
h2 { text-align: center; }
input { width: 100%; padding: 8px; margin: 6px 0; box-sizing: border-box; }
button { width: 100%; padding: 10px; background: #007bff; color: white; border: none; cursor: pointer; }
.admin-card { border-top: 3px solid #dc3545; margin-top: 30px; }
.error { color: red; text-align: center; }
</style>
</head>
<body>

<div class="card">
  <h2>User Login</h2>
  <form action="login" method="post">
    <input type="text" name="username" placeholder="User Username" required><br>
    <input type="password" name="password" placeholder="Password" required><br>
    <button type="submit">Login</button>
  </form>
</div>

<div class="card admin-card">
  <h2>Admin Login</h2>
  <form action="adminLogin" method="post">
    <input type="text" name="adminUsername" placeholder="Admin Username" required><br>
    <input type="password" name="adminPassword" placeholder="Password" required><br>
    <button type="submit">Login as Admin</button>
  </form>
  <div style="text-align:center; margin-top:8px;">
    <small>Admin sample: admin@gmail.com / adminyash18 (inserted into SQL script)</small>
  </div>
</div>

</body>
</html>
