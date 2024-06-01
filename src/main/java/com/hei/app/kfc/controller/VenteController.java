package com.hei.app.kfc.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hei.app.kfc.model.entity.Vente;
import com.hei.app.kfc.service.VenteService;

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
}
