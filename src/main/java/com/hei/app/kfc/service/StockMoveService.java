package com.hei.app.kfc.service;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hei.app.kfc.model.entity.StockMove;
import com.hei.app.kfc.repository.StockMoveRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StockMoveService {
    private final StockMoveRepository repository;

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
}
