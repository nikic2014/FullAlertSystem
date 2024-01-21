package com.AlertSystem.AlertAPI.repositories;

import com.AlertSystem.AlertAPI.models.PeopleInProject;
import com.AlertSystem.AlertAPI.models.PeopleInProjectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeopleInProjectRepository extends JpaRepository<PeopleInProject, PeopleInProjectId> {

    List<PeopleInProject> findByPeopleInProjectIdIdPeople(int idPeople);
    List<PeopleInProject> findByPeopleInProjectIdIdProject(int idProject);
    List<PeopleInProject> findByPeopleInProjectIdIdProjectAndRole(int idProject, String Role);
}
