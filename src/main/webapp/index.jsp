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
    <title>G. Store</title>
</head>

<body>
    <div id='topbar'>
        <nav>
            <button onclick: "onVegetablesButtonClicked()">Vegetables</button>
            <button onclick: "onFruitsButtonClicked()">Fruits</button>
            <button onclick: "onMiscButtonClicked()">Misc</button>
            <button onclick: "onContactButtonClicked()">Contact</button>
            <button onclick: "onAboutUsButtonClicked()">About Us</button>
            <button onclick: "onLoginButtonClicked()">Login</button>
            <button onclick: "onLogoutButtonClicked()" class='hidden content'>Login</button>
        </nav>
    </div>
    <div id='dropdown' class='hidden content'></div>
    <div id='profile-content' class='hidden content'></div>
    <div id='cart' class='hidden content'</div>
    <div id='page-content'></div>
</body>

</html>