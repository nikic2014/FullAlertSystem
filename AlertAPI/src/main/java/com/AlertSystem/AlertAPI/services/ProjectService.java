package com.AlertSystem.AlertAPI.services;

import com.AlertSystem.AlertAPI.models.Project;
import com.AlertSystem.AlertAPI.repositories.ProjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public void saveProject(Project project){
        this.projectRepository.save(project);
    }

    public Project findByIdProject(int idProject) throws Exception {
        Optional<Project> project = this.projectRepository.findById(idProject);
        if (project.isEmpty())
            throw new Exception("Такого проекта не найдено");

        return project.get();
    }
}