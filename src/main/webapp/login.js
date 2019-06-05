function loginResponse() {
    if (this.status === OK) {
        const user = JSON.parse(this.responseText);
        setUser(user);
        onPageLoad(user);
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
