package com.example.springbootjpasample;

import com.example.springbootjpasample.model.Person;
import com.example.springbootjpasample.repository.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class PersonRepositoryTest {

    @Autowired
    private PersonRepository repository;

    @Test
    void shouldFindById() {
        Assertions.assertNotNull(repository);
        Optional<Person> person = repository.findById(1L);
        Assertions.assertNotNull(person);
    }

    @Test
    void shouldInsertPerson() {
        Person person = createPerson();
        Person insert = repository.save(person);
        Assertions.assertNotNull(insert);
        Assertions.assertNotNull(insert.getId());
    }

    @Test
    void shouldDelete() {
        Person person = createPerson();
        Person insert = repository.save(person);
        repository.deleteById(insert.getId());
        Optional<Person> empty = repository.findById(insert.getId());
        Assertions.assertTrue(empty.isEmpty());
    }

    @Test
    void shouldUpdate() {
        Person person = createPerson();
        Person insert = repository.save(person);

        insert.update(Person.builder()
                .name("Maria")
                .surname("Jose")
                .age(22)
                .build());
        repository.save(insert);
        Optional<Person> newPerson =repository.findById(insert.getId());
        Assertions.assertEquals(insert.getName(),newPerson.get().getName());
    }

    @Test
    void shouldFindAll() {

        List<Person> persons = new ArrayList<>();
        for (int index = 0; index < 10; index++) {
            Person person = createPerson();
            persons.add(repository.save(person));
        }
        Pageable page = PageRequest.of(0, 2);
        List<Person> result = StreamSupport.stream(repository.findAll(page).spliterator(), false)
                .collect(Collectors.toList());
        Assertions.assertEquals(2, result.size());
    }

    private Person createPerson(){
        Person person = Person.builder()
                .name("Jose")
                .surname("Silva")
                .age(34)
                .build();
        return person;
    }
}
