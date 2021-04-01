package com.example.springbootjpasample;

import com.example.springbootjpasample.model.Person;
import com.example.springbootjpasample.repository.PersonRepository;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
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
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository repository;

    @Test
    public void shouldFindById() {
        Assertions.assertNotNull(repository);
        Optional<Person> Person = repository.findById(1L);
        Assertions.assertNotNull(Person);
    }

    @Test
    public void shouldInsertPerson() {
        Person person = createPerson();
        Person insert = repository.save(person);
        Assertions.assertNotNull(insert);
        Assertions.assertNotNull(insert.getId());
    }

    @Test
    public void shouldDelete() {
        Person person = createPerson();
        Person insert = repository.save(person);
        repository.deleteById(insert.getId());
        Optional<Person> empty = repository.findById(insert.getId());
        Assertions.assertTrue(empty.isEmpty());
    }

    @Test
    public void shouldUpdate() {
        Person person = createPerson();
        Person insert = repository.save(person);

        insert.update(Person.builder()
                .name("Maria")
                .surname("Jose")
                .age(22)
                .build());
        repository.save(insert);
    }

    @Test
    public void shouldFindAll() {

        List<Person> Persons = new ArrayList<>();
        for (int index = 0; index < 10; index++) {
            Person person = createPerson();
            Persons.add(repository.save(person));
        }
        Pageable page = PageRequest.of(0, 2);
        List<Person> result = StreamSupport.stream(repository.findAll(page).spliterator(), false)
                .collect(Collectors.toList());
        Assertions.assertEquals(2, result.size());
    }

    public Person createPerson(){
        Person person = Person.builder()
                .name("Jose")
                .surname("Silva")
                .age(34)
                .build();
        return person;
    }
}
