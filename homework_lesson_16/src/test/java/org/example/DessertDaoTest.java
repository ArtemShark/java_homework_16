package org.example;

import org.example.dao.dessertDAO.DessertsDaoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

import org.example.model.Dessert;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class DessertDaoTest {
    private DessertsDaoImpl dessertsDao;

    @BeforeEach
    public void setUp() throws Exception {
        String url = "jdbc:postgresql://localhost:5436/homework1-db";
        String user = "sa";
        String password = "admin";
        Connection connection = DriverManager.getConnection(url, user, password);
        dessertsDao = new DessertsDaoImpl(connection);

        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM desserts");
        }
    }

    @Test
    public void insertDesserts() {
        dessertsDao.save(new Dessert(0, "Cheesecake", "Чизкейк", BigDecimal.valueOf(4.00)));
        dessertsDao.save(new Dessert(0, "Tiramisu", "Тирамису", BigDecimal.valueOf(5.00)));
        dessertsDao.save(new Dessert(0, "Apple Pie", "Яблочный пирог", BigDecimal.valueOf(3.50)));
        dessertsDao.save(new Dessert(0, "Brownie", "Брауни", BigDecimal.valueOf(4.25)));
        dessertsDao.save(new Dessert(0, "Ice Cream", "Мороженое", BigDecimal.valueOf(2.75)));

        List<Dessert> desserts = dessertsDao.findAll();
        assertEquals(5, desserts.size());
    }
}
