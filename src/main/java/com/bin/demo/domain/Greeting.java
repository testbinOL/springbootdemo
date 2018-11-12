package com.bin.demo.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Greeting implements Serializable {
    private final long id;
    private final String content;

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }
}
