function onLogoutResponse() {
    if (this.status === OK) {
        clearMessages();
        showContents(['home-page-content'])
    } else {
        onOtherResponse(logoutButtonDivEl, this);
    }
}

function onLogoutButtonClicked(event) {
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onLogoutResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', '/logout');
    xhr.send();
}
