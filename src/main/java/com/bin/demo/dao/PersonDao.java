package com.bin.demo.dao;


import com.bin.demo.domain.Person;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonDao extends JpaRepository<Person, Integer> {

    @Override
    List<Person> findAll(Sort sort);

    List<Person> findByIdInOrderByAgeDesc(List<Long> ids);

    @Query("FROM Person where age = :age")
    Person getByAge(@Param("age") Integer a);
}
