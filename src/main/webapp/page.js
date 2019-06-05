function onPageLoad() {
    clearMessages();
    const user = getUser();
    if (user === null) {
        alert('No user.');
        showContents(['page-content', 'login-button', 'sign-up-button', 'cart-button']);
    } else if (user.role === 'USER') {
        alert('User user.');
        showContents(['page-content','logout-button', 'orders-button']);
    } else if (user.role === 'ADMIN') {
        alert('Admin user.');
        showContents(['page-content','logout-button', 'orders-button']);
    }
}

function onLoginButtonClicked() {
    showContents(['login-content']);
}

function onSignUpButtonClicked() {
    showContents(['sign-up-content']);
}

function onProductsButtonClicked() {
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onProductsReceived);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'product');
    xhr.send();
}

function onProductsReceived() {
    const text = this.responseText;
    const products = JSON.parse(text);
    document.getElementById('products-content').appendChild(createProductsDropdown(products));
}

function createProductsDropdown(products) {
    const ulEl = document.createElement('ul');

    for (let i = 0; i < products.length; i++) {
        const product = products[i];

        const productButtonEl = document.createElement('button');
        productButtonEl.setAttribute('data-product-id', product.id);
        productButtonEl.textContent = product.name;
        productButtonEl.addEventListener('click', onProductClicked);

        ulEl.appendChild(productButtonEl);
    }
    return ulEl;
}

function onProductClicked() {
}