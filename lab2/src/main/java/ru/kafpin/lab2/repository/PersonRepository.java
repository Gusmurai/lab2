package ru.kafpin.lab2.repository;

import org.springframework.data.repository.CrudRepository;
import ru.kafpin.lab2.entity.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {
}
