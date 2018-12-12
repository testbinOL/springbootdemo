package com.bin.demo.service;

import com.bin.demo.domain.Person;

import java.util.List;

public interface PersonService {
    Person getById();

    List<Person> getAll();

    List<Person> getByIds(List<Long> ids);

    Person getByAge(Integer age);
}
