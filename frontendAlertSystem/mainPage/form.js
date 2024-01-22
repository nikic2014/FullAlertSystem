function toggleForm() {
    const form = document.getElementById('projectForm');
    form.style.display = form.style.display === 'none' ? 'block' : 'none';
}

function addParticipant() {
    const participantsContainer = document.getElementById('participants');
    const newParticipant = document.createElement('div');
    newParticipant.classList.add('participant');
    newParticipant.innerHTML = `
        <input type="text" name="participantId" placeholder="ID участника" required>
        <input type="text" name="participantRole" placeholder="Роль участника" required>
        <span class="remove-participant" onclick="removeParticipant(this)">❌</span>
    `;
    participantsContainer.appendChild(newParticipant);
}

function removeParticipant(element) {
    const participantContainer = element.parentElement;
    participantContainer.remove();
}

function submitForm() {
    const form = document.getElementById('createProjectForm');
    const formData = new FormData(form);

    formData.forEach((value, key) => {
        console.log(`${key}: ${value}`);
    });

    const participants = [];
    formData.getAll('participantId').forEach((id, index) => {
        participants.push({
            "participantId": id,
            "participantRole": formData.getAll('participantRole')[index]
        });
    });

    const projectData = {
        "projectName": formData.get('projectName'),
        "projectDescription": formData.get('projectDescription'),
        "participants": participants
    };

    console.log(projectData.participants)

    const jsonData = JSON.stringify(projectData);

    fetch('http://localhost:8080/createProject', {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json', // Указываем тип контента как JSON
        },  
        body: jsonData
    })
    .then(response => {
        if (response.ok) {
            return response.text();
        }
        throw new Error('Network response was not ok');
    })
    .then(data => {
        console.log('Project created:', data);
        // Дополнительная обработка успешного ответа
        alert("Проект успешно создан")
        toggleForm(); // Скрываем форму после успешного создания проекта
        location.reload();
    })
    .catch(error => {
        console.error('Error during project creation:', error);
        // Обработка ошибки
    });
}