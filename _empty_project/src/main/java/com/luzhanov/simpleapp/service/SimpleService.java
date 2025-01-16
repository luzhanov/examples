package com.luzhanov.simpleapp.service;

import lombok.ToString;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.StringUtils.isBlank;

@ToString
@Service
public class SimpleService {

    public String sayHello(String name) {
        if (isBlank(name)) {
            throw new IllegalArgumentException("Name cannot be blank");
        }

        return "Hello " + name;
    }
}
