package com.bin.demo.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author: xingshulin
 * @Date: 2018/12/10 下午7:13
 * @Description: TODO
 * @Version: 1.0
 **/
@Entity
@Table(name = "t_person")
@Data
public class Person {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private Integer age;
}
