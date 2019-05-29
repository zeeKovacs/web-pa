function onProfileLoad(user) {
    clearMessages();
    showContents(['profile-content', 'logout-content']);

    const userEmailSpandEl = document.getElementById('user-email');
    const userPasswordSpanEl = document.getElementById('user-password');

    userEmailSpandEl.textContent = user.email;
    userPasswordSpanEl.textContent = user.password;
}