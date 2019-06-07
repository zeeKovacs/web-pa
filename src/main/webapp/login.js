function loginResponse() {
    if (this.status === OK) {
        const user = JSON.parse(this.responseText);
        setUser(user);
        savedCartExists()
        showSpecificContent();
    } else {
        onOtherResponse(loginContentDivEl, this);
    }
}

function loginButtonClicked() {
    const loginFormEl = document.forms['login-form'];

    const emailInputEl = loginFormEl.querySelector('input[name="email"]');
    const passwordInputEl = loginFormEl.querySelector('input[name="password"]');

    const email = emailInputEl.value;
    const password = passwordInputEl.value;

    const params = new URLSearchParams();
    params.append('email', email);
    params.append('password', password);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', loginResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'login');
    xhr.send(params);
}

function savedCartExists() {
    const user = getUser();

    const params = new URLSearchParams();
    params.append("user-id", user.id);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', savedCartReceived);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'cart?' + params.toString());
    xhr.send();
}

function savedCartReceived() {
    const text = this.responseText;
    const cart = JSON.parse(text);
    setCart(cart);
}