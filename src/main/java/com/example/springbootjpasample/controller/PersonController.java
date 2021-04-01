package com.example.springbootjpasample.controller;

import com.example.springbootjpasample.model.PersonDTO;
import com.example.springbootjpasample.service.PersonService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService service;

    public PersonController(PersonService service) {
        this.service = service;
    }

    @GetMapping
    public List<PersonDTO> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "size", defaultValue = "10") int size) {
        return service.findAll(PageRequest.of(page, size));
    }

    @GetMapping("{id}")
    public PersonDTO findById(@PathVariable Long id) {
        Optional<PersonDTO> Person = service.findById(id);
        return Person.orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
                "Unable to find the Person " + id));
    }

    @PostMapping
    public ResponseEntity<PersonDTO> insert(@RequestBody PersonDTO dto) {
        return new ResponseEntity<>(service.insert(dto), HttpStatus.CREATED);
    }

    @PutMapping(value = "{id}")
    public PersonDTO update(@PathVariable("id") Long id, @RequestBody PersonDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping(value = "{id}")
    public void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }
}
