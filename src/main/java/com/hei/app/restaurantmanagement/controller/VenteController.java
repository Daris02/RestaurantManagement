package com.hei.app.restaurantmanagement.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hei.app.restaurantmanagement.model.entity.Vente;
import com.hei.app.restaurantmanagement.service.VenteService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/ventes")
public class VenteController {
    private final VenteService service;

    @GetMapping({"", "/"})
    public List<Vente> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Vente getById(@PathVariable("id") Integer id) {
        return service.getById(id);
    }

    @PostMapping({"", "/"})
    public List<Vente> create(@RequestBody List<Vente> ventes) {
        return service.saveAll(ventes);
    }

    @GetMapping("/summary")
    public Object summaryMenus(
        @RequestParam(value = "start", required = false) String start,
        @RequestParam(value = "end", required = false) String end ) {
            return service.summaryMenus(start, end);
        }
}
