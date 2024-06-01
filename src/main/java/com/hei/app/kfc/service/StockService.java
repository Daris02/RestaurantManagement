package com.hei.app.kfc.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.hei.app.kfc.model.entity.Stock;
import com.hei.app.kfc.model.entity.StockMove;
import com.hei.app.kfc.repository.StockRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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

    public Object getMovesDetails(Integer restaurantId, String startDate, String endDate) {
        List<Stock> stocks = repository.findAllByRestaurantId(restaurantId);
        Instant startInstant = Timestamp.valueOf(startDate + " 00:00:00").toInstant().minusSeconds(14400);
        Instant endInstant = Timestamp.valueOf(endDate + " 00:00:00").toInstant().minusSeconds(14400);
        stocks.stream().filter(s -> s.getCreateAt().isAfter(endInstant) && s.getCreateAt().isBefore(endInstant));
        return Map.of(startInstant, endInstant);
    }
}
