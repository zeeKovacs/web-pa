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

function showSpecificContent(additional) {
    const user = getUser()

    if (userExists() && user.role === 'ADMIN') {
        alert('Admin.');
        showContents(['orders-button', 'logout-button', additional]);
    } else if (userExists() && user.role === 'USER') {
        alert('User.');
        showContents(['cart-button', 'orders-button', 'logout-button', additional]);
    } else {
        alert('Guest.');
        showContents(['cart-button', 'login-button', 'sign-up-button', additional]);
    }
}

function onLoginButtonClicked() {
    showSpecificContent('login-content');
}

function onSignUpButtonClicked() {
    showSpecificContent('sign-up-content');
}

function onProductsButtonClicked() {
    showSpecificContent('products-content');

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
    showSpecificContent('product-page');
    const productContentEl = document.getElementById('product-page');
    removeAllChildren(productContentEl);

    const product = evt.target.product;
    console.log(product.name)

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
        const product_id = this.getAttribute('product-id');
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
        onOtherResponse(document.getElementById('cart-content'), this);
    }
}

function onCartButtonClicked() {
    showSpecificContent('cart-content');
    removeAllChildren(document.getElementById('cart-content'));

    if (!cartExists() && !userExists()) {
        const cartContentEl = document.getElementById('cart-content');

        const pEl = document.createElement('p');
        pEl.textContent = 'Your cart is empty.';

        cartContentEl.appendChild(pEl);
        return;

    } else {
        const cart = getCart();

        const params = new URLSearchParams();
        params.append('cart-id', cart.id);

        const xhr = new XMLHttpRequest();
        xhr.addEventListener('load', cartContentResponse);
        xhr.addEventListener('error', onNetworkError);
        xhr.open('GET', 'cartItem?' + params.toString());
        xhr.send();
    }
}

function cartContentResponse() {
    const text = this.responseText;
    const cartItems = JSON.parse(text);
    fillCartContent(cartItems);
}

function fillCartContent(cartItems) {
    const cartContentEl = document.getElementById('cart-content');
    const ulEl = document.createElement('ul');
    let total = 0;

        for (let i = 0; i < cartItems.length; i++) {
            const item = cartItems[i];

            total += item.price;

            const picEl = document.createElement('img');
            picEl.setAttribute('src', item.picture);

            const pEl = document.createElement('p');
            pEl.textContent = item.name + ' ' + item.quantity + '' + item.unit + ' ' + item.price + ' ft';

            const liEl = document.createElement('li');
            liEl.appendChild(picEl);
            liEl.appendChild(pEl);
            ulEl.appendChild(liEl);
        }
        cartContentEl.appendChild(ulEl);
}