package com.example.springbootjpasample.repository;

import com.example.springbootjpasample.model.Person;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PersonRepository extends PagingAndSortingRepository<Person, Long> {
}
