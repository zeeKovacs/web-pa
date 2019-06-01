function onPageLoad() {
    clearMessages();
    showContents(['homepage-content', 'homepage-nav']);
}

function continueAsAGuestClicked() {
    showContents(['main-page-content','sign-up-button', 'page-content']);
}

function loginButtonClicked() {
    showContents(['homepage-content', 'login-content']);
}

function signUpButtonClicked() {
    showContents(['homepage-content', 'sign-up-content']);
}