package com.hei.app.kfc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ping")
public class PingController {
    @GetMapping({"", "/"})
    public String pingPong(){
        return "pong";
    }
}
