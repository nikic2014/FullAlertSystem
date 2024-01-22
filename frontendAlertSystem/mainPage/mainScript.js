function logout(){
    // document.cookie = "jwt-token" + '= none; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';
    window.location.href = 'http://127.0.0.1:5500/authPage/authPage.html';
    console.log("Пользователь разлогинен");
}

function OpCloseReregister(){
    var reregisterForm = document.getElementById('reregisterForm');
    reregisterForm.style.display = reregisterForm.style.display == 'none' ? 'block' : 'none';
}

function reregister(){
    const url = 'http://localhost:8080/user/reregister';
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
        credentials: 'include',
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
                alert("Данные пользователя успешно изменены");
                OpCloseReregister();           
            } else {
                // Если статус неуспешен, обработка ошибки
                alert("Ошибка перерегистрации");
            }
        });
}


function loadPage(){
    var usernameElement = document.getElementById('Username');
    var idElement = document.getElementById('ID');

    const url = 'http://localhost:8080/user/userInfo';
    const options = {
        method: 'GET',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json', // Указываем тип контента как JSON
        },
    };

    fetch(url, options)
    .then(response => {
        if (response.ok) {
            return response.json(); // Return the parsed JSON
        } else {
            alert("Ошибка входа");
            throw new Error('Authentication failed'); // Throw an error to jump to the catch block
        }
    })
    .then(data => {
        var newUsername = data.username;
        var newID = 'ID: ' + data.id;

        // Assuming usernameElement and idElement are defined somewhere in your code
        var usernameElement = document.getElementById('Username'); // Replace 'usernameElement' with the actual ID or class of your username element
        var idElement = document.getElementById('ID'); // Replace 'idElement' with the actual ID or class of your ID element

        // Update the text content of elements
        if (usernameElement && idElement) {
            usernameElement.textContent = newUsername;
            idElement.textContent = newID;
        } else {
            console.log('Elements not found');
        }

        console.log(data);
        loadProjects(data.id);
    })
    .catch(error => {
        // Handle the error, e.g., redirect to an error page
        window.location.href = 'http://127.0.0.1:5500/authPage/authPage.html';
        console.error('Error:', error);
    });
}

// Получение токена
// const jwtToken = localStorage.getItem("jwtToken");

loadPage();


