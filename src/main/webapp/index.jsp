<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="/style.css">
        <script src="/index.js"></script>
        <script src="/login.js"></script>
        <script src="/profile.js"></script>
        <script src="back-to-profile.js"></script>
        <script src="/logout.js"></script>
        <title>App</title>
    </head>
<body>
<div id="login-content" class="content">
    <h1>Login</h1>
    <form id="login-form" onsubmit="return false;">
        <input type="text" name="email">
        <input type="password" name="password">
        <button id="login-button">Login</button>
    </form>
</div>
<div id="profile-content" class="hidden content">
    <h1>Profile</h1>
    <p>Email: <span id="user-email"></span></p>
    <p>Password: <span id="user-password"></span></p>
    <h2>Links</h2>
</div>
<div id="back-to-profile-content" class="hidden content">
    <button onclick="onBackToProfileClicked();">Back to profile</button>
</div>
<div id="logout-content" class="hidden content">
    <button id="logout-button">Logout</button>
</div>
</body>
</html>
