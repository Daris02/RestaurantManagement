package com.hei.app.kfc.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.hei.app.kfc.model.AutoCRUD;
import com.hei.app.kfc.model.entity.StockMove;
import com.hei.app.kfc.model.enums.MoveType;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class StockMoveRepository extends AutoCRUD<StockMove, Integer> {
    @Override
    protected String getTableName() {
        return "stockmove";
    }

    @Override
    protected StockMove mapResultSetToEntity(ResultSet resultSet) {
        try {
            return new StockMove(
                resultSet.getInt("id"),
                resultSet.getDouble("value"),
                resultSet.getTimestamp("createAt").toInstant(),
                resultSet.getInt("ingredientId"),
                resultSet.getInt("restaurantId"),
                MoveType.valueOf(resultSet.getString("type"))
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
