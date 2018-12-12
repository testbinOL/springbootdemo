package com.bin.demo.controller;

import com.bin.demo.domain.Greeting;
import com.bin.demo.domain.Person;
import com.bin.demo.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@Slf4j
public class GreetingController {

    private static final String template = "Hello,%s!,%s";

    private final AtomicInteger counter = new AtomicInteger();

    @Autowired
    private PersonService personService;

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "age", defaultValue = "10") Integer age) {
        //List<Person> persons = personService.getAll();
        /*List<Long> ids = persons.stream().map(person -> person.getId()).collect(Collectors.toList());
        List<Person> personList = personService.getByIds(ids);*/
        Person person = personService.getByAge(age);
        /*log.info("日志级别：{}", persons.toString());
        log.debug("日志: {}", persons.toString());*/
        String result = String.format(template, person != null ? person.getName() : "NULL", person != null ? person.toString() : "NULL");
        return new Greeting(counter.incrementAndGet(), result);
    }
}
