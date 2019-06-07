function onPageLoad() {
    clearMessages();
    if (userExists()) {
        const user = getUser();

        const email = user.email;
        const password = user.password;

        const params = new URLSearchParams();
        params.append('email', email);
        params.append('password', password);

        const xhr = new XMLHttpRequest();
        xhr.addEventListener('load', loginResponse);
        xhr.addEventListener('error', onNetworkError);
        xhr.open('POST', 'login');
        xhr.send(params);
    }
    showSpecificContent();
}

function showSpecificContent() {
    const user = getUser()

    if (userExists() && user.role === 'ADMIN') {
        alert('Admin.');
    } else if (userExists() && user.role === 'USER') {
        alert('User.');
    } else {
        alert('Guest.');
        showContents(['login-button']);
    }
}

function onLoginButtonClicked() {
    showContents(['login-content']);
}

function onSignUpButtonClicked() {
    showContents(['sign-up-content']);
}

function onProductsButtonClicked() {
    showContents(['login-button', 'sign-up-button', 'cart-button', 'products-content']);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onProductsReceived);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'product');
    xhr.send();
}

function onProductsReceived() {
    const text = this.responseText;
    const products = JSON.parse(text);
    fillProductsContent(products);
}

function fillProductsContent(products) {
    const productsContentEl = document.getElementById('products-content');
    removeAllChildren(productsContentEl);

    for (let i = 0; i < products.length; i++) {
        const product = products[i];

        const aEl = document.createElement('a');
        aEl.href='javascript:void(0);';
        aEl.setAttribute('data-product-id', product.id);
        aEl.addEventListener('click', onProductClicked);

        const picEl = document.createElement('img');
        picEl.setAttribute('src', product.picture);
        picEl.product = product;
        aEl.appendChild(picEl);

        productsContentEl.appendChild(aEl);
    }
}

function onProductClicked(evt) {
    showContents(['login-button', 'sign-up-button', 'cart-button', 'product-page']);
    const productContentEl = document.getElementById('product-page');
    removeAllChildren(productContentEl);

    const product = evt.target.product;

    const picEl = document.createElement('img');
    picEl.setAttribute('src', product.picture);

    const inputEl = document.createElement('input');
    inputEl.type = 'number';
    if (product.unit === 'kg') {
        inputEl.setAttribute('step', '0.1');
        inputEl.setAttribute('placeholder', 'Kilogrammes');
    } else {
        inputEl.setAttribute('step', '1');
        inputEl.setAttribute('placeholder', 'Each');
    }
    inputEl.setAttribute('min', '0');
    inputEl.id = 'quantity';
    inputEl.addEventListener('input', calcProductPrice);
    inputEl.product = product;

    const pEl = document.createElement('p');
    pEl.id = 'dynamic-price';
    pEl.textContent = 'Price: 0 Ft';

    const buttonEl = document.createElement('button');
    buttonEl.textContent = 'Add to cart';
    buttonEl.id = 'add-to-cart-button';
    buttonEl.setAttribute('product-id', product.id);
    buttonEl.addEventListener('click', onAddToCartClicked);

    productContentEl.appendChild(picEl);
    productContentEl.appendChild(inputEl);
    productContentEl.appendChild(pEl);
    productContentEl.appendChild(buttonEl);
}

function calcProductPrice(evt) {
    const quantity = document.getElementById('quantity').value;
    const product = evt.target.product;

    const pEl = document.getElementById('dynamic-price');
    pEl.textContent = 'Price: ' + (quantity * product.price).toFixed(2) + ' Ft';
}

function onAddToCartClicked() {
        let cart = getCart();

        if (cart === null) {
            createCart();
        }

        cart = getCart();

        const quantityInputEl = document.getElementById('quantity')
        const quantity = quantityInputEl.value;
        const product_id = this.getAttribute("product-id");
        const cart_id = cart.id;

        const params = new URLSearchParams();
        params.append('quantity', quantity);
        params.append('product-id', product_id);
        params.append('cart-id', cart_id);

        const xhr = new XMLHttpRequest();
        xhr.addEventListener('load', onAddToCartResponse);
        xhr.addEventListener('error', onNetworkError);
        xhr.open('POST', 'cartItem');
        xhr.send(params);
}

function onAddToCartResponse() {
    if (this.status === OK) {
        onPageLoad()
    } else {
        onOtherResponse(document.getElementById('add-to-cart-button'), this);
    }
}

function createCart() {
        const user = getUser()

        const params = new URLSearchParams();
        if (user !== null) {
            params.append('user-id', user.id);
        }

        const xhr = new XMLHttpRequest();
        xhr.addEventListener('load', createCartResponse);
        xhr.addEventListener('error', onNetworkError);
        xhr.open('POST', 'cart', false);
        xhr.send(params);
}

function createCartResponse() {
    if (this.status === OK) {
        const cart = JSON.parse(this.responseText);
        setCart(cart);
    } else {
        onOtherResponse(document.getElementById('add-to-cart-button'), this);
    }
}