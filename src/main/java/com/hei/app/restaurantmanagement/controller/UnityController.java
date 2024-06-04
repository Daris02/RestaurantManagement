package com.hei.app.restaurantmanagement.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hei.app.restaurantmanagement.model.entity.Unity;
import com.hei.app.restaurantmanagement.service.UnityService;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@AllArgsConstructor
@RequestMapping("/unities")
public class UnityController {
    private final UnityService service;
    
    @GetMapping({"", "/"})
    public List<Unity> getAll() {
        return service.getAll();
    }
    
    @GetMapping("/{id}")
    public Unity getById(@PathVariable("id") Integer id) {
        return service.getById(id);
    }
    
    @PostMapping({"", "/"})
    public List<Unity> create(@RequestBody List<Unity> unities) {
        return service.saveAll(unities);
    }
    
}
