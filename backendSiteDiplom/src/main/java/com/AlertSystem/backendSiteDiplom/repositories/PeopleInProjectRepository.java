package com.AlertSystem.backendSiteDiplom.repositories;

import com.AlertSystem.backendSiteDiplom.models.PeopleInProject;
import com.AlertSystem.backendSiteDiplom.models.PeopleInProjectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeopleInProjectRepository extends JpaRepository<PeopleInProject, PeopleInProjectId> {

    List<PeopleInProject> findByPeopleInProjectIdIdPeople(int idPeople);
    List<PeopleInProject> findByPeopleInProjectIdIdProject(int idProject);
}
