package com.hei.app.restaurantmanagement.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hei.app.restaurantmanagement.model.entity.Stock;
import com.hei.app.restaurantmanagement.model.entity.StockMove;
import com.hei.app.restaurantmanagement.service.StockMoveService;
import com.hei.app.restaurantmanagement.service.StockService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@AllArgsConstructor
@RequestMapping("/stocks")
public class StockController {
    private final StockService service;
    private final StockMoveService stockMoveService;

    @GetMapping({"", "/"})
    public List<Stock> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Stock getById(@PathVariable("id") Integer id) {
        return service.getById(id);
    }

    @PostMapping("/supply")
    public List<Stock> create(@RequestBody List<StockMove> stocks) {
        return service.saveAll(stocks);
    }

    @GetMapping("/movesdetails/{restaurantId}")
    public Object getMovesDetails(
        @PathVariable("restaurantId") Integer restaurantId,
        @RequestParam(value = "start", required = false) String start,
        @RequestParam(value = "end", required = false) String end ) {
        return stockMoveService.getMovesDetails(restaurantId, start, end);
    }
}
