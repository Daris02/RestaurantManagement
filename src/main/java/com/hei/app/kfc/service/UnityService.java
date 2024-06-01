package com.hei.app.kfc.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.hei.app.kfc.model.entity.Unity;
import com.hei.app.kfc.repository.UnityRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UnityService {
    private final UnityRepository repository;

    public Unity getById(Integer id) {
        return repository.getById(id);
    }

    public List<Unity> getAll() {
        return repository.findAll();
    }

    public List<Unity> saveAll(List<Unity> toSaves) {
        return toSaves.stream().map(this::save).toList();
    }

    private Unity save(Unity toSave) {
        repository.save(toSave);
        Optional<Unity> unity = repository.findAll().stream()
                .max(Comparator.comparingInt(Unity::getId))
                .stream().findFirst();
        return unity.orElse(toSave);
    }

}
