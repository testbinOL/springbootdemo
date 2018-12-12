package com.bin.demo.service.impl;

import com.bin.demo.dao.PersonDao;
import com.bin.demo.domain.Person;
import com.bin.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: xingshulin
 * @Date: 2018/12/10 下午11:23
 * @Description: TODO
 * @Version: 1.0
 **/
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonDao personDao;

    @Override
    public Person getById() {
        return null;
    }

    @Override
    public List<Person> getAll() {
        return personDao.findAll(Sort.by(Sort.Order.asc("age"), Sort.Order.desc("id")));
    }

    @Override
    public List<Person> getByIds(List<Long> ids) {
        return personDao.findByIdInOrderByAgeDesc(ids);
    }

    @Override
    public Person getByAge(Integer age) {
        return personDao.getByAge(age);
    }
}
