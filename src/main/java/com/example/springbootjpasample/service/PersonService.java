package com.example.springbootjpasample.service;

import com.example.springbootjpasample.exception.EntityNotFoundException;
import com.example.springbootjpasample.model.Person;
import com.example.springbootjpasample.model.PersonDTO;
import com.example.springbootjpasample.model.PersonMapper;
import com.example.springbootjpasample.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class PersonService {
    private final PersonRepository repository;

    private final PersonMapper mapper;

    @Autowired
    public PersonService(PersonRepository repository, PersonMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<PersonDTO> findAll(Pageable page) {

        Stream<Person> stream = StreamSupport
                .stream(repository.findAll(page)
                        .spliterator(), false);
        return stream.map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<PersonDTO> findById(Long id) {
        return repository.findById(id).map(mapper::toDTO);
    }

    public PersonDTO insert(PersonDTO dto) {
        Person Person = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(Person));
    }

    public PersonDTO update(Long id, PersonDTO dto) {
        Person Person = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person does not find with the id " + id));
        Person.update(mapper.toEntity(dto));
        repository.save(Person);
        return mapper.toDTO(Person);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
