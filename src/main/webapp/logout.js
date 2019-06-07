function onLogoutResponse() {
    if (this.status === OK) {
        clearMessages();
        emptyStorage();
        onPageLoad();
    } else {
        onOtherResponse(logoutButtonEl, this);
    }
}

function onLogoutButtonClicked(event) {
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onLogoutResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/logout');
    xhr.send();
}
