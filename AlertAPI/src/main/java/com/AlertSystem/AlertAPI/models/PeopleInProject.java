package com.AlertSystem.AlertAPI.models;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "PeopleInProject")
public class PeopleInProject {

    @EmbeddedId
    PeopleInProjectId peopleInProjectId;

    @Column(name = "role")
    private String role;

    public PeopleInProject(){}

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public PeopleInProjectId getPeopleInProjectId() {
        return peopleInProjectId;
    }

    public void setPeopleInProjectId(PeopleInProjectId peopleInProjectId) {
        this.peopleInProjectId = peopleInProjectId;
    }
}