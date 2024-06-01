package com.hei.app.kfc.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.hei.app.kfc.model.AutoCRUD;
import com.hei.app.kfc.model.entity.Unity;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class UnityRepository extends AutoCRUD<Unity, Integer> {
    @Override
    protected String getTableName() {
        return "unity";
    }

    @Override
    protected Unity mapResultSetToEntity(ResultSet resultSet) {
        try {
            return new Unity(
                resultSet.getInt("id"),
                resultSet.getString("name")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
