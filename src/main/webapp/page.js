function onPageLoad() {
    clearMessages();
    if (isUserAdmin()) {
        alert('Admin user.');
        showContents(['logout-button', 'orders-button']);
    } if (isUserUser()) {
        alert('User user.');
        showContents(['logout-button', 'orders-button']);
    } else {
        alert('No user.');
        showContents(['login-button', 'sign-up-button', 'cart-button']);
    }
}

function onLoginButtonClicked() {
    showContents(['login-content']);
}

function onSignUpButtonClicked() {
    showContents(['sign-up-content']);
}

function onProductsButtonClicked() {
    showContents(['products-content']);

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
}