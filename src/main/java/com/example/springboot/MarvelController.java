package com.example.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/")
    public String index() {
        logger.debug("mingtest debug");
        logger.warn("mingtest warn");
        logger.info("mingtest info");
        logger.error("mingtest error");

        System.out.println(System.getProperty("java.version"));
        System.out.println(System.getProperty("java.specification.version"));

        return "Greetings from Spring Boot!";
    }

}
