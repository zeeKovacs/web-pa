function onPageLoad() {
    clearMessages();
    showContents(['homepage-content', 'homepage-nav']);
}

function continueAsAGuestClicked() {
    showContents(['main-page-content', 'page-content']);
}

function loginButtonClicked() {
    showContents(['homepage-content', 'login-content']);
}