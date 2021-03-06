package com.ibingbo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @RequestMapping("/hello")
    public String hello() {
        return "hello,consul two";
    }

    @RequestMapping("/hi")
    public String hi(@RequestParam("name") String name) {
        return "two hello " + name;
    }
}
