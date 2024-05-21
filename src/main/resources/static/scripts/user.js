document.addEventListener("DOMContentLoaded", function () {
    const loginInput = document.getElementById('login');
    const newPasswordInput = document.getElementById('newPassword');
    const confirmPasswordInput = document.getElementById('confirmPassword');
    const form = document.querySelector('.newUser form'); // Используем общую форму

    // Функция для проверки наличия логина
    function checkLoginExistence() {
        // Проверка наличия логина в списке
        if (AllLogins.includes(loginInput.value)) {
            alert('Этот логин уже используется другим пользователем. Пожалуйста, выберите другой.');
            return false;
        }
        return true;
    }

    // Функция для проверки соответствия паролей
    function checkPasswordsMatch() {
        if (newPasswordInput.value !== confirmPasswordInput.value) {
            alert('Пароли не совпадают. Пожалуйста, введите одинаковые пароли.');
            return false;
        }
        return true;
    }

    form.addEventListener('submit', function(event) {
        // Проверяем логин
        if (!checkLoginExistence()) {
            event.preventDefault(); // Остановка отправки формы если логин уже используется
            return;
        }

        // Проверяем пароли
        if (!checkPasswordsMatch()) {
            event.preventDefault(); // Остановка отправки формы если пароли не совпадают
        }
    });
});