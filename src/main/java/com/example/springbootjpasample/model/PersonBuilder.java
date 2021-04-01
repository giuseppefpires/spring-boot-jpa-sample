package com.example.springbootjpasample.model;

public class PersonBuilder {
    private Long id;
    private String name;
    private String surname;
    private int age;

    public PersonBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public PersonBuilder name(String name) {
        this.name = name;
        return this;
    }

    public PersonBuilder surname(String surname) {
        this.surname = surname;
        return this;
    }

    public PersonBuilder age(int age) {
        this.age = age;
        return this;
    }



    public Person build() {
        return new Person(id, name, surname, age);
    }
}
