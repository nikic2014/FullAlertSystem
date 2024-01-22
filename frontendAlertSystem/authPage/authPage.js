$('.message a').click(function(){
    $('form').animate({height: "toggle", opacity: "toggle"}, "slow");
 });


 function register() {
    const url = 'http://localhost:8080/auth/register';
    const username = document.querySelector('.register-form input[placeholder="username"]').value;
    const password = document.querySelector('.register-form input[placeholder="password"]').value;
    const email = document.querySelector('.register-form input[placeholder="email address"]').value;
    const telephone = document.querySelector('.register-form input[placeholder="telephone number"]').value;
    const tgLink = document.querySelector('.register-form input[placeholder="tg link"]').value;

    const data = { "login": username, "password": password, "email": email, "telephone": telephone, "tgLink": tgLink};
    // Преобразуем объект данных в строку JSON
    const jsonData = JSON.stringify(data);

    // Опции для запроса
    const options = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json', // Указываем тип контента как JSON
        },
        body: jsonData, // Передаем данные в теле запроса
    };

    // Выполняем POST-запрос
    fetch(url, options)
        .then(response => {
            // Проверка статуса ответа
            if (response.ok) {
                // Если статус успешен (200-299), обработка данных
                console.log(response.text()); // Возвращаем текст ответа
                alert("Пользователь успешно зарегестрирован");
                $('form').animate({height: "toggle", opacity: "toggle"}, "slow");             
            } else {
                // Если статус неуспешен, обработка ошибки
                alert("Ошибка регистрации");
            }
        });
}

function login() {
    const url = 'http://localhost:8080/auth/login';
    const username = document.querySelector('.login-form input[placeholder="username"]').value;
    const password = document.querySelector('.login-form input[placeholder="password"]').value;

    const data = { "login": username, "password": password};
    // Преобразуем объект данных в строку JSON
    const jsonData = JSON.stringify(data);

    const options = {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json', // Указываем тип контента как JSON
        },
        body: jsonData, // Передаем данные в теле запроса
    };

    fetch(url, options)
    .then(response => {
        // Проверка статуса ответа
        if (response.ok) {
            return response.text(); // Возвращаем Promise с текстом ответа
        } else {
            // Если статус неуспешен, обработка ошибки
            alert("Ошибка входа");
            throw new Error('Authentication failed'); // Вызываем ошибку для перехода к блоку catch
        }
    })
    .then(data => {
        console.log(data.jwt);
        alert("Пользователь успешно вошел");
        window.location.href = 'http://127.0.0.1:5500/mainPage/main.html'; // Переход на другую страницу
    })
    .catch(error => {
        console.error('Error:', error);
    });
}