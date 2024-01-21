package com.AlertSystem.AlertAPI.services;

import com.AlertSystem.AlertAPI.models.People;
import com.AlertSystem.AlertAPI.repositories.PeopleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PeopleService {
    private final PeopleRepository peopleRepository;

    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public People getById(int id) throws Exception {
        Optional<People> people = peopleRepository.findById(id);

        if (people.isEmpty())
            throw new Exception("User not found");

        return people.get();
    }
}
