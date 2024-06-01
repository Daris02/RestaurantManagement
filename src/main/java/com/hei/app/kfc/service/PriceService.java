package com.hei.app.kfc.service;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.hei.app.kfc.model.entity.Price;
import com.hei.app.kfc.repository.PriceRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PriceService {
    private final PriceRepository repository;

    public Price getById(Integer id) {
        return repository.getByIdRecent(id);
    }

    public List<Price> getAll() {
        return repository.findAll();
    }

    public List<Price> saveAll(List<Price> toSaves) {
        return toSaves.stream().map(this::save).toList();
    }

    public Price save(Price toSave) {
        toSave.setCreateAt(Instant.now());
        repository.save(toSave);
        Optional<Price> priceSaved = repository.findAll().stream()
                .max(Comparator.comparingInt(Price::getId))
                .stream().findFirst();
        return priceSaved.orElse(toSave);
    }
    
}
