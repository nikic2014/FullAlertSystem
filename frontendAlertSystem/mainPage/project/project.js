// Пример данных о проекте (замените на свои реальные данные)
// var project = {
//     id: 1,
//     label: 'Project XYZ',
//     description: 'This is a sample project description.',
//     participants: [
//         { name: 'John Doe', email: 'john@example.com', phone: '+123456789' },
//         { name: 'Jane Doe', email: 'jane@example.com', phone: '+987654321' }
//         // Добавьте своих участников
//     ]
// };

// Функция для отображения информации о проекте
function displayProjectInfo(project) {
    console.log(project);
    var projectInfoContainer = document.getElementById('project-info');
    var projectInfo = document.createElement('div');
    projectInfo.className = 'project-info';

    var projectName = document.createElement('h2');
    projectName.textContent = 'Project name: ' + project.label;

    var projectId = document.createElement('p');
    projectId.textContent = 'Project ID: ' + project.id;

    var projectDescription = document.createElement('p');
    projectDescription.innerHTML = 'Description:';
    var projectDescription2 = document.createElement('p');
    projectDescription2.innerHTML = project.description;

    projectInfo.appendChild(projectName);
    projectInfo.appendChild(projectId);
    projectInfo.appendChild(projectDescription);
    projectInfo.appendChild(projectDescription2);

    projectInfoContainer.appendChild(projectInfo);
}

// Функция для отображения участников проекта
function displayParticipants(participants) {
    console.log(participants);
    var participantsContainer = document.getElementById('participants');

    participants.forEach(function(participant, index) {
        var participantBlock = document.createElement('div');
        participantBlock.className = 'participant-block';

        var participantName = document.createElement('h3');
        participantName.textContent = 'Participant ' + (index + 1) + ': ' + participant.people.login;

        var participantInfo = document.createElement('div');
        participantInfo.className = 'participant-info';
        participantInfo.innerHTML = '<p>Role: ' + participant.role + '</p>' +
                                    '<p>Email: ' + participant.people.email + '</p>' +
                                    '<p>Phone: ' + participant.people.telephone + '</p>'+
                                    '<p>Phone: ' + participant.people.tgLink + '</p>';

        participantBlock.appendChild(participantName);
        participantBlock.appendChild(participantInfo);

        participantsContainer.appendChild(participantBlock);
    });
    if (participants.length == 0){
        participantsContainer.innerHTML = "В данном проекте нет участников.";
        participantsContainer.style.margin = '20px';
        participantsContainer.style.fontWeight = "bold";
    }
}


function getProject(id){
    const url = 'http://localhost:8080/getProjectInfo/' + id;
    const options = {
        method: 'GET',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json', // Указываем тип контента как JSON
        },
    };

    return fetch(url, options)
    .then(response => {
        if (response.ok) {
            return response.json(); // Return the parsed JSON
        } else {
            alert("Ошибка запроса");
            throw new Error('Bad request project');
        }
    })
    .then(obj => {
        return obj;
    })
    .catch((error) => {
        console.error("Promise отклонено:", error);
        // Здесь можно обработать ошибку, если обещание было отклонено
    });
}

function getParticipants(id){
    const url = 'http://localhost:8080/getAllPeople/' + id;
    const options = {
        method: 'GET',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json', // Указываем тип контента как JSON
        },
    };

    return fetch(url, options)
    .then(response => {
        if (response.ok) {
            return response.json(); // Return the parsed JSON
        } else {
            alert("Ошибка запроса");
            throw new Error('Bad request partispants');
        }
    })
    .then(obj => {
        return obj; 
    })
    .catch((error) => {
        console.error("Promise отклонено:", error);
        // Здесь можно обработать ошибку, если обещание было отклонено
    }); 
}

// Вызов функций при загрузке страницы
document.addEventListener('DOMContentLoaded', function() {
    function getQueryParam(name) {
        const urlParams = new URLSearchParams(window.location.search);
        return urlParams.get(name);
    }

    var projectId = getQueryParam('id');
    console.log(projectId);

    getProject(projectId).then(result => {
        console.log(result);
        displayProjectInfo(result);
    })
    .catch(error => {
        // Обработка ошибок
        console.error(error);
    });

    getParticipants(projectId).then(result => {
        console.log(result);
        displayParticipants(result);
    })
    .catch(error => {
        // Обработка ошибок
        console.error(error);
    });
    
});