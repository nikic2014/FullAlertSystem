package com.AlertSystem.AlertAPI.repositories;

import com.AlertSystem.AlertAPI.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    Optional<Project> findById(int idProject);
}

