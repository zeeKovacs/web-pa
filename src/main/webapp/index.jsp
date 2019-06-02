<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="style.css">
    <script src="index.js"></script>
    <script src="login.js"></script>
    <script src="main-page.js"></script>
    <script src="logout.js"></script>
    <script src="homepage.js"></script>
    <script src="sign-up.js"></script>
    <title>Grocery Store</title>
</head>

<body>
    <div id="homepage-content" class="hidden content">
        <h1><a href="javascript:void(0);" onclick="location.reload();">EAT SMART. SHOP SMART.<a/></h1>
        <nav id="homepage-nav" class="hidden content">
            <button onclick="loginButtonClicked();">Login</button>
            <button onclick="signUpButtonClicked();">Sign Up</button><br>
            <a href="javascript:void(0);" onclick="continueAsAGuestClicked();">Continue as a guest</a>
        </nav>
        <div id="login-content" class="hidden content">
            <form id="login-form" onsubmit="return false;">
                <input type="email" name="email" placeholder="email address"><br>
                <input type="password" name="password" placeholder="password"><br>
                <button id="login-button" onclick="onLoginButtonClicked();">Login</button>
            </form>
        </div>
        <div id="sign-up-content" class="hidden content">
            <form id="sign-up-form" onsubmit="return false;">
                <input type="text" name="name" placeholder="name">
                <input type="email" name="email" placeholder="email">
                <input type="password" name="password" placeholder="password">
                <input type="text" name="phone_number"placeholder="Phone: +36/XX-XXX-XXX"><br>
                <button id="sign-up-button" onclick="onSignUpButtonClicked();">Sign Up</button>
            </form>
        </div>
    </div>
    <div id="main-page-content" class="hidden content">
        <div id="top-bar-content">
            <nav id="main-page-nav">
                <button onclick="onProductsButtonClicked();">Products</button>
                <button onclick="onMiscButtonClicked();">Misc</button>
                <button onclick="onContactButtonClicked();">Contact</button>
                <button onclick="onAboutUsButtonClicked();">About Us</button>
                <button id="sign-up-button" onclick="signUpButtonClicked();" class="hidden-content">Sign Up</button>
                <button id="login-button" onclick="loginButtonClicked();" class="hidden content">Login</button>
                <button id="logout-button" onclick="onLogoutButtonClicked();" class="hidden content">Logout</button>
            </nav>
        </div>
        <div id="page-content" class="hidden content"></div>
    </div>
    <div id="dropdown-content" class="hidden content"></div>
    <div id="profile-content" class="hidden content"></div>
    <div id="cart-content" class="hidden content"</div>
</body>

</html>