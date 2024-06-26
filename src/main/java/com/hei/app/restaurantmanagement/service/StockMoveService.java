package com.hei.app.restaurantmanagement.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hei.app.restaurantmanagement.model.entity.StockMove;
import com.hei.app.restaurantmanagement.repository.StockMoveRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StockMoveService {
    private final StockMoveRepository repository;
    private final IngredientService ingredientService;
    private final UnityService unityService;
    private final ZoneId ZONEID = ZoneId.of("UTC+3");

    public StockMove getById(Integer id) {
        return repository.getById(id);
    }

    public List<StockMove> findAll() {
        return repository.findAll();
    }
    
    public List<StockMove> saveAll(List<StockMove> toSaves) {
        return toSaves.stream().map(this::save).toList();
    }

    public StockMove save(StockMove toSave) {
        toSave.setCreateAt(Instant.now());
        repository.save(toSave);
        Optional<StockMove> stockMove = repository.findAll().stream()
                .max(Comparator.comparingInt(StockMove::getId))
                .stream().findFirst();
        return stockMove.orElse(toSave);
    }

    public Object getMovesDetails(Integer restaurantId, String startDate, String endDate) {
        List<StockMove> stockMoves = repository.findAllByRestaurantId(restaurantId);
        Instant startInstant = dateStringToInstant(startDate);
        Instant endInstant = dateStringToInstant(endDate);
        List<StockMove> stockMovesFiltered = stockMoves.stream().filter(s -> (s.getCreateAt().isAfter(startInstant) && s.getCreateAt().isBefore(endInstant))).toList();
        List<Map<String, Object>> all = new ArrayList<>();
        for (StockMove stockMove : stockMovesFiltered) {
            Map<String, Object> result = new LinkedHashMap<>();
            result.put("Date et Heure", dateFormatter(stockMove.getCreateAt()));
            result.put("Ingredient", ingredientService.getById(stockMove.getIngredientId()).getName());
            result.put("Type de Mouvement", stockMove.getType());
            result.put("Qte", stockMove.getValue());
            result.put("Unite", unityService.getById(ingredientService.getById(stockMove.getIngredientId()).getUnityId()).getName());
            all.add(result);
        }
        return all;
    }
    
    private Instant dateStringToInstant(String dateString) {
        return Timestamp.valueOf(dateString + " 00:00:00").toInstant().minusSeconds(14400);
    }

    private String dateFormatter(Instant instant) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZONEID);
        return dateTime.format(formatter);
    }
}
