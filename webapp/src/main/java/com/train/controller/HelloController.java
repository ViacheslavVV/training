package com.train.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
public class HelloController {

    @PostConstruct
    public void init() {
        System.out.println("+++++++++++++++++++++++++++++++++++++++ HelloController initialized +++++++++++++++++++++++++++++++++++++++++++++");
    }

    @GetMapping("/")
    public String getIndex() {
        return "hello";
    }

    @GetMapping("/welcome")
    public String getWelcome() {
        return "welcome ";
    }
}
