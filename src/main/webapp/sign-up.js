function signUpButtonClicked() {
    const signUpFormEl = document.forms['sign-up-form'];

    const nameInputEl = signUpFormEl.querySelector('input[name="name"]');
    const emailInputEl = signUpFormEl.querySelector('input[name="email"]');
    const passwordInputEl = signUpFormEl.querySelector('input[name="password"]');
    const phoneNumberInputEl = signUpFormEl.querySelector('input[name="phone_number"]');

    const name = nameInputEl.value;
    const email = emailInputEl.value;
    const password = passwordInputEl.value;
    const phone_number = phoneNumberInputEl.value;

    const params = new URLSearchParams();
    params.append('name', name);
    params.append('email', email);
    params.append('password', password);
    params.append('phone_number', phone_number);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', signUpResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'signUp');
    xhr.send(params);
}

function signUpResponse() {
    if (this.status === OK) {
        const user = JSON.parse(this.responseText);
        setUser(user);
        onPageLoad(user);
    } else {
        onOtherResponse(homepageContentDivEl, this);
    }
}
