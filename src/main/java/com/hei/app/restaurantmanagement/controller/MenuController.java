package com.hei.app.restaurantmanagement.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hei.app.restaurantmanagement.model.entity.Menu;
import com.hei.app.restaurantmanagement.service.MenuService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/menus")
public class MenuController {
    private final MenuService service;

    @GetMapping({"", "/"})
    public List<Menu> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Menu getById(@PathVariable("id") Integer id) {
        return service.getById(id);
    }

    @PostMapping({"", "/"})
    public List<Menu> create(@RequestBody List<Menu> menus) {
        return service.saveAll(menus);
    }
}
