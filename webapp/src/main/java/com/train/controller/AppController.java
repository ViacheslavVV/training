package com.train.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/app")
public class AppController {

    @PostConstruct
    public void init() {
        System.out.println("+++++++++++++++++++++++++++++++++++++++  AppController initialized  +++++++++++++++++++++++++++++++++++++++++++++");
    }

    @GetMapping
    public String getIndex() {
        return "hello app";
    }

    @GetMapping("/welcome")
    public String getWelcome() {
        return "welcome app";
    }
}
