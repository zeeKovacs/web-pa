<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>

<head>
    <link href="https://fonts.googleapis.com/css?family=Comfortaa:500&display=swap" rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="style.css">
    <script src="sign-up.js"></script>
    <script src="logout.js"></script>
    <script src="index.js"></script>
    <script src="login.js"></script>
    <script src="page.js"></script>
    <meta charset="UTF-8">
    <title>Grocery Store</title>
</head>

<body>
    <div class="wrapper">
        <div id="page-content">
            <nav id="top-nav">
                <p>GROCERY STORE, EGER</p><br>
                <button id="products-button" onclick="onProductsButtonClicked();">Products</button>
                <button id="cart-button" onclick="onCartButtonClicked()" class="hidden content">Cart</button>
                <button id="orders-button" onclick="onOrdersButtonClicked();" class="hidden content">Sign Up</button>
                <button id="sign-up-button" onclick="onSignUpButtonClicked();" class="hidden content">Sign Up</button>
                <button id="login-button" onclick="onLoginButtonClicked();" class="hidden content">Login</button>
                <button id="logout-button" onclick="onLogoutButtonClicked();" class="hidden content">Logout</button>
            </nav>
            <div id="products-content">
                <a href="https://picsum.photos/200/200"><img border="0" src="https://picsum.photos/200/200" width="200" height="200">
                <a href="https://picsum.photos/200/200"><img border="0" src="https://picsum.photos/200/200" width="200" height="200">
                <a href="https://picsum.photos/200/200"><img border="0" src="https://picsum.photos/200/200" width="200" height="200">
                <a href="https://picsum.photos/200/200"><img border="0" src="https://picsum.photos/200/200" width="200" height="200">
                <a href="https://picsum.photos/200/200"><img border="0" src="https://picsum.photos/200/200" width="200" height="200">
                <a href="https://picsum.photos/200/200"><img border="0" src="https://picsum.photos/200/200" width="200" height="200">
                <a href="https://picsum.photos/200/200"><img border="0" src="https://picsum.photos/200/200" width="200" height="200">
                <a href="https://picsum.photos/200/200"><img border="0" src="https://picsum.photos/200/200" width="200" height="200">
                <a href="https://picsum.photos/200/200"><img border="0" src="https://picsum.photos/200/200" width="200" height="200">
                <a href="https://picsum.photos/200/200"><img border="0" src="https://picsum.photos/200/200" width="200" height="200">
                <a href="https://picsum.photos/200/200"><img border="0" src="https://picsum.photos/200/200" width="200" height="200">
                <a href="https://picsum.photos/200/200"><img border="0" src="https://picsum.photos/200/200" width="200" height="200">
                <a href="https://picsum.photos/200/200"><img border="0" src="https://picsum.photos/200/200" width="200" height="200">
                <a href="https://picsum.photos/200/200"><img border="0" src="https://picsum.photos/200/200" width="200" height="200">
                <a href="https://picsum.photos/200/200"><img border="0" src="https://picsum.photos/200/200" width="200" height="200">
                <a href="https://picsum.photos/200/200"><img border="0" src="https://picsum.photos/200/200" width="200" height="200">
                <a href="https://picsum.photos/200/200"><img border="0" src="https://picsum.photos/200/200" width="200" height="200">
                <a href="https://picsum.photos/200/200"><img border="0" src="https://picsum.photos/200/200" width="200" height="200">
                <a href="https://picsum.photos/200/200"><img border="0" src="https://picsum.photos/200/200" width="200" height="200">
                <a href="https://picsum.photos/200/200"><img border="0" src="https://picsum.photos/200/200" width="200" height="200">
            </div>
            <div id="login-content" class="hidden content">
                <form id="login-form" onsubmit="return false;">
                    <input type="email" name="email" placeholder="email address"><br>
                    <input type="password" name="password" placeholder="password"><br>
                    <button id="login-button" onclick="loginButtonClicked();">Login</button>
                </form>
            </div>
            <div id="sign-up-content" class="hidden content">
                <form id="sign-up-form" onsubmit="return false;">
                    <input type="text" name="name" placeholder="name">
                    <input type="email" name="email" placeholder="email">
                    <input type="password" name="password" placeholder="password">
                    <input type="text" name="phone_number"placeholder="Phone: +36/XX-XXX-XXX"><br>
                    <button id="sign-up-button" onclick="signUpButtonClicked();">Sign Up</button>
                </form>
            </div>
            <div id="profile-content" class="hidden content"></div>
            <div id="cart-content" class="hidden content"</div>
        </div>
     </div>
</body>

</html>