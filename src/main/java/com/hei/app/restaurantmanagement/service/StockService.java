package com.hei.app.restaurantmanagement.service;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.hei.app.restaurantmanagement.model.entity.Stock;
import com.hei.app.restaurantmanagement.model.entity.StockMove;
import com.hei.app.restaurantmanagement.repository.StockRepository;

@Service
@AllArgsConstructor
public class StockService {
    private final StockRepository repository;
    private final StockMoveService stockMoveService;
    private final IngredientService ingredientService;

    public Stock getById(Integer id) {
        return repository.getById(id);
    }

    public List<Stock> getAll() {
        return repository.findAll();
    }

    public List<Stock> saveAll(List<StockMove> toSaves) {
        return toSaves.stream().map(this::save).toList();
    }

    public Stock save(StockMove toSave) {
        toSave.setCreateAt(Instant.now());

        Stock newStock = new Stock();
        newStock.setCreateAt(toSave.getCreateAt());
        newStock.setIngredientId(toSave.getIngredientId());
        newStock.setRestaurantId(toSave.getRestaurantId());
        Stock currentStock = repository.getByIngredientId(toSave.getIngredientId());

        switch (toSave.getType()) {
            case ENTRY:
                if (currentStock != null) newStock.setValue(currentStock.getValue() + toSave.getValue());
                newStock.setValue(toSave.getValue());
                break;
            case OUT:
                if (currentStock != null) newStock.setValue(currentStock.getValue() - toSave.getValue());
                else throw new RuntimeException("Stock not existing of this ingredient : " + ingredientService.getById(toSave.getIngredientId()).getName());
        }

        stockMoveService.save(toSave);
        repository.save(newStock);

        Optional<Stock> stock = repository.findAll().stream()
                .max(Comparator.comparingInt(Stock::getId))
                .stream().findFirst();
        return stock.orElse(toSave);
    }

    public Stock getByIngredientId(Integer id) {
        return repository.getByIngredientId(id);
    }
}
