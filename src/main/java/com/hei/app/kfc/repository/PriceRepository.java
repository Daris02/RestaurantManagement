package com.hei.app.kfc.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.hei.app.kfc.model.AutoCRUD;
import com.hei.app.kfc.model.entity.Price;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class PriceRepository extends AutoCRUD<Price, Integer> {
    @Override
    protected String getTableName() {
        return "price";
    }

    @Override
    protected Price mapResultSetToEntity(ResultSet resultSet) {
        try {
            return new Price(
                resultSet.getInt("id"),
                resultSet.getDouble("value"),
                resultSet.getTimestamp("createAt").toInstant()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
