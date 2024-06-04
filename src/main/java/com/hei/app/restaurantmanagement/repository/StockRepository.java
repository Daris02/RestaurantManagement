package com.hei.app.restaurantmanagement.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.hei.app.restaurantmanagement.config.ConnectionDB;
import com.hei.app.restaurantmanagement.model.AutoCRUD;
import com.hei.app.restaurantmanagement.model.entity.Stock;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class StockRepository extends AutoCRUD<Stock, Integer> {
    @Override
    protected String getTableName() {
        return "stock";
    }

    @Override
    protected Stock mapResultSetToEntity(ResultSet resultSet) {
        try {
            return new Stock(
                resultSet.getInt("id"),
                resultSet.getDouble("value"),
                resultSet.getTimestamp("createAt").toInstant(),
                resultSet.getInt("ingredientId"),
                resultSet.getInt("restaurantId")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Stock> findAllByRestaurantId(Integer id) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Stock> stocks = new ArrayList<>();
        String query = "SELECT * FROM stock " +
                        " WHERE restaurantid = " + id + 
                        " AND createAt bet;";

        try {
            connection = ConnectionDB.createConnection();
            assert connection != null;
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                stocks.add(mapResultSetToEntity(resultSet));
            }
            return stocks;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
