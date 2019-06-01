<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="style.css">
    <script src="index.js"></script>
    <script src="login.js"></script>
    <script src="page.js"></script>
    <script src="logout.js"></script>
    <script src="home-page.js"></script>
    <title>Grocery Store</title>
</head>

<body>
    <div id='homepage-content' class='hidden content'>
        <h1>EAT SMART. SHOP SMART.</h1>
        <nav id='homepage-nav' class='hidden content'>
            <button onclick="loginButtonClicked();">Login</button>
            <button onclick="signUpButtonClicked();">Sign Up</button><br>
            <a href='javascript:void(0);' onclick="continueAsAGuestClicked();">Continue as a guest</a>
        </nav>
        <div id="login-content" class="hidden content">
            <form id="login-form" onsubmit="return false;">
                <input type="text" name="email">
                <input type="password" name="password">
                <button id="login-button" onclick='onLoginButtonClicked();'>Login</button>
            </form>
        </div>
            <form id="sign-up-form" onsubmit="return false;" class='hidden content'>
            <input type="text" name="name">
            <input type="text" name="email">
            <input type="password" name="password">
            <input type="text" name="phone_number">
            <button id="sign-up-button" onclick='onSignUpButtonClicked();'>Sign Up</button>
        </form>
    </div>
    <div id='main-page-content' class='hidden content'>
        <div id='topbar-content'>
            <nav id='main-page-nav'>
                <button onclick="onProductsButtonClicked();">Products</button>
                <button onclick="onMiscButtonClicked();">Misc</button>
                <button onclick="onContactButtonClicked();">Contact</button>
                <button onclick="onAboutUsButtonClicked();">About Us</button>
                <button onclick="onLoginButtonClicked();" class='hidden content'>Login</button>
                <button onclick="onLogoutButtonClicked();" class='hidden content'>Login</button>
            </nav>
        </div>
        <div id='page-content' class='hidden content'></div>
    </div>
    <div id='dropdown-content' class='hidden content'></div>
    <div id='profile-content' class='hidden content'></div>
    <div id='cart-content' class='hidden content'</div>
</body>

</html>