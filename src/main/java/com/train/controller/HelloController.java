package com.train.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class HelloController {

    @RequestMapping(method = RequestMethod.GET)
    public String getIndex() {
        return "hello";
    }

    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String getWelcome() {
        return "welcome";
    }
}
