package com.example.springbootjpasample.model;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    Person toEntity(PersonDTO dto);

    PersonDTO toDTO(Person car);
}
