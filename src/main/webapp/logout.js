function onLogoutResponse() {
    if (this.status === OK) {
        clearMessages();
        emptyStorage();
        const user = null;
        onPageLoad(user);
    } else {
        onOtherResponse(logoutButtonDivEl, this);
    }
}

function onLogoutButtonClicked(event) {
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onLogoutResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'logout');
    xhr.send();
}
