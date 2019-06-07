const OK = 200;
const BAD_REQUEST = 400;
const UNAUTHORIZED = 401;
const NOT_FOUND = 404;
const INTERNAL_SERVER_ERROR = 500;

let loginContentDivEl;
let signUpContentDivEl;
let logoutButtonEl;

function newInfo(targetEl, message) {
    newMessage(targetEl, 'info', message);
}

function newError(targetEl, message) {
    newMessage(targetEl, 'error', message);
}

function newMessage(targetEl, cssClass, message) {
    clearMessages();

    const pEl = document.createElement('p');
    pEl.classList.add('message');
    pEl.classList.add(cssClass);
    pEl.textContent = message;

    targetEl.appendChild(pEl);
}

function clearMessages() {
    const messageEls = document.getElementsByClassName('message');
    for (let i = 0; i < messageEls.length; i++) {
        const messageEl = messageEls[i];
        messageEl.remove();
    }
}

function showContents(ids) {
    const contentEls = document.getElementsByClassName('content');
    for (let i = 0; i < contentEls.length; i++) {
        const contentEl = contentEls[i];
        if (ids.includes(contentEl.id)) {
            contentEl.classList.remove('hidden');
        } else {
            contentEl.classList.add('hidden');
        }
    }
}

function removeAllChildren(el) {
    while (el.firstChild) {
        el.removeChild(el.firstChild);
    }
}

function onNetworkError(response) {
    document.body.remove();
    const bodyEl = document.createElement('body');
    document.appendChild(bodyEl);
    newError(bodyEl, 'Network error, please try reloading the page');
}

function onOtherResponse(targetEl, xhr) {
    if (xhr.status === NOT_FOUND) {
        newError(targetEl, 'Not found');
        console.error(xhr);
    } else {
        const json = JSON.parse(xhr.responseText);
        if (xhr.status === INTERNAL_SERVER_ERROR) {
            newError(targetEl, `Server error: ${json.message}`);
        } else if (xhr.status === UNAUTHORIZED || xhr.status === BAD_REQUEST) {
            newError(targetEl, json.message);
        } else {
            newError(targetEl, `Unknown error: ${json.message}`);
        }
    }
}

function setUser(user) {
    return localStorage.setItem('user', JSON.stringify(user));
}

function setCart(cart) {
    return localStorage.setItem('cart', JSON.stringify(cart));
}

function getUser() {
    return JSON.parse(localStorage.getItem('user'));
}

function getCart() {
    return JSON.parse(localStorage.getItem('cart'));
}

function cartExists() {
    return localStorage.getItem('cart') !== null;
}

function userExists() {
    return localStorage.getItem('user') !== null;
}

function emptyStorage() {
    return localStorage.removeItem('user', 'cart');
}

function isUserAdmin() {
    const user = getUser();
    if (user === null) {
        return false;
    } if (user.role === 'ADMIN') {
        return true;
    } return false;
}

function isUserUser() {
    const user = getUser();
    if (user === null) {
        return false;
    } if (user.role === 'USER') {
        return true;
    } return false;
}

function onLoad() {
    loginContentDivEl = document.getElementById('login-content');
    signUpContentDivEl = document.getElementById('sign-up-content');
    logoutButtonEl = document.getElementById('logout-button');

    onPageLoad();
}

document.addEventListener('DOMContentLoaded', onLoad);
