package com.hei.app.restaurantmanagement.model;

import java.util.List;

public interface CRUD<T, ID> {
    T getById(ID id);

    List<T> findAll();

    T save(T toSave);
}
