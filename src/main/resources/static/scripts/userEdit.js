document.addEventListener("DOMContentLoaded", function () {
    const loginInput = document.getElementById('login');
    const formProfile = document.querySelector('.editInfo form');

    formProfile.addEventListener('submit', function(event) {
        if (logins.includes(loginInput.value) && loginInput.value !== currentLogin) {
            alert('Этот логин уже используется другим пользователем. Пожалуйста, выберите другой.');
            event.preventDefault(); // Остановить отправку формы
        }
    });

    const formPassword = document.querySelector('.editPswrd form');
    const newPasswordInput = document.getElementById('newPassword');
    const confirmPasswordInput = document.getElementById('confirmPassword');

    formPassword.addEventListener('submit', function(event) {
        if (newPasswordInput.value !== confirmPasswordInput.value) {
            alert('Пароли не совпадают. Пожалуйста, введите одинаковые пароли.');
            event.preventDefault(); // Остановить отправку формы
        }
    });
});