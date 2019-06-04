function onPageLoad(user) {
    clearMessages();
    if (user === null) {
        alert('No user.');
        showContents(['homepage-content', 'homepage-nav']);
    } else if (user.role === 'GUEST') {
        alert('Guest user.');
        showContents(['main-page-content','sign-up-button', 'page-content']);
    } else if (user.role === 'USER') {
        alert('User user.');
        showContents(['main-page-content','logout-button', 'page-content']);
    } else if (user.role === 'ADMIN') {
        alert('Admin user.');
        showContents(['main-page-content','logout-button', 'page-content']);
    }
}

function onContinueAsGuestResponse() {
    if (this.status === OK) {
        const user = JSON.parse(this.responseText);
        setUser(user);
        onPageLoad(user);
    } else {
        onOtherResponse(loginContentDivEl, this);
    }
}

function continueAsAGuestClicked() {
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onContinueAsGuestResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'login');
    xhr.send();
}

function loginButtonClicked() {
    showContents(['homepage-content', 'login-content']);
}

function signUpButtonClicked() {
    showContents(['homepage-content', 'sign-up-content']);
}