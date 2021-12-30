package com.train.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    //TODO логгер подключи себе другой, это встроенный java логгер
    // для демонстрации пойдет
    public static Logger logger = LogManager.getLogManager().getLogger("");

    //TODO Ошибки при обработке обычных RequestParam и RequestVariable (не все возможные)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler({ConstraintViolationException.class,
            MissingRequestValueException.class})
    public void handleValidationException(Throwable e) {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        logger.info("any exception \n" + stringWriter);
    }

    //TODO ошибки при обработке DTO
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(BindException.class)
    public void handleValidationException(BindException e) {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        logger.info("Bind DTO exception \n" + stringWriter);
    }

}
