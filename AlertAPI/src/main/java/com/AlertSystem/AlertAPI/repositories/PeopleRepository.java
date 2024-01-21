package com.AlertSystem.AlertAPI.repositories;

import com.AlertSystem.AlertAPI.models.People;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<People, Integer> {
    Optional<People> findByLogin(String login);
}