package com.train.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Max;

@RestController("/")
@Validated
public class HelloController {

    @RequestMapping(path = "/value", method = RequestMethod.GET)
    public String getByValue( @Max(12) @RequestParam Integer value) {
        //TODO правильная ссылка, по которой можно пройти валидацию
        // http://localhost:8090/value?value=10
        return "hello";
    }

    @RequestMapping(path = "/dto", method = RequestMethod.GET)
    public String getByDto( @Valid HelloDto dto) {
        //TODO правильная ссылка, по которой можно пройти валидацию
        // http://localhost:8090/dto?name=Vlad&email=vlad@mail.ru&age=18
        // http://localhost:8090/dto?name=Vlad&age=18    (email не обязателен, но если указан - то проверяется)
        return "hello";
    }
}
