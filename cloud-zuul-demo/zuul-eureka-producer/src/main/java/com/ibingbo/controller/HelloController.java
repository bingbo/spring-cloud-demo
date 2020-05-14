package com.ibingbo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    private final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @RequestMapping("/hello")
    public String hello(@RequestParam String name) {
        return "hello " + name;
    }

    @RequestMapping("/hi")
    public String hi() {
        logger.info("request hi method ----");
        try {
            Thread.sleep(1000000);
        } catch (Exception e) {
            logger.error("hi method error", e);
        }
        return "hi guy";
    }
}
