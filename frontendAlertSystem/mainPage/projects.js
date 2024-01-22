function exitProject(projectId) {
    var projectBlock = document.getElementById(projectId);

    if (projectBlock) {
        const url = 'http://localhost:8080/exitOfProject/' + projectId;
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
                return response.text(); // Return the text Promise
            } else {
                alert("Ошибка выхода из проекта");
                throw new Error('Bad request');
            }
        })
        .then(text => {
            console.log(text); // Log the text
        })
        .catch((error) => {
            console.error("Ошибка выхода из проекта:", error);
            // Здесь можно обработать ошибку, если обещание было отклонено
        });

        projectBlock.remove();
    } else {
        console.log('Проект не найден');
    }
}

function generateProjectBlock(projectId, projectName, projectDescription){
    var projectBlock = document.createElement('div');
    projectBlock.className = 'project-block';
    projectBlock.id = projectId;
    
    var infoBlock = document.createElement('div');
    infoBlock.className = 'info-block';

    infoBlock.addEventListener('click', function() {
        window.location.href = 'project/project.html?id=' + projectId; // Замените на URL вашей страницы проекта
    });

    var projectLabel = document.createElement('div');
    projectLabel.className = 'project-label';
    var projectLabelHeading = document.createElement('h3');
    projectLabelHeading.textContent = projectName;
    projectLabel.appendChild(projectLabelHeading);

    var projectDescriptionDiv = document.createElement('div');
    projectDescriptionDiv.className = 'project-description';
    projectDescriptionDiv.textContent = projectDescription;

    infoBlock.appendChild(projectLabel);
    infoBlock.appendChild(projectDescriptionDiv);

    var exitButton = document.createElement('button');
    exitButton.className = 'exit-project-btn';
    exitButton.textContent = 'X';
    function createExitFunction(projectId) {
        return function() {
            exitProject(projectId);
        };
    }
    exitButton.onclick = createExitFunction(projectId);

    projectBlock.appendChild(infoBlock);
    projectBlock.appendChild(exitButton);

    return projectBlock;
}



function loadProjects(id){
    const url = 'http://localhost:8080/getAllProjects/'+id;
    const options = {
        method: 'GET',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json', // Указываем тип контента как JSON
        }
    };

    fetch(url, options)
    .then(response => {
        if (response.ok) {
            return response.json(); // Return the parsed JSON
        } else {
            alert("Ошибка запроса");
            throw new Error('Bad request');
        }
    })
    .then(data => {
        console.log(data);
        var nonProject = document.getElementsByClassName('non-project-block')[0];

        if (data.massage != undefined){
            nonProject.style.display = 'block';
        }
        else {
            nonProject.style.display = 'none';
        
            for (var i = 0; i < data.length; i++) {
                var container = document.getElementsByClassName('projectsContainer')[0];

                var project1Block = generateProjectBlock(data[i].id, data[i].label, data[i].description);
                container.appendChild(project1Block);
            }
        }
    })
    .catch(error => {
        // Handle the error, e.g., redirect to an error page
        console.error('Error:', error);
    });

}