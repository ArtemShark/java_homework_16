package org.example;


import org.example.dao.drinkDAO.DrinksDaoImpl;
import org.example.model.Drink;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class DrinkDaoTest {
    private DrinksDaoImpl drinksDao;

    @BeforeEach
    public void setUp() throws Exception {
        String url = "jdbc:postgresql://localhost:5436/homework1-db";
        String user = "sa";
        String password = "admin";
        Connection connection = DriverManager.getConnection(url, user, password);
        drinksDao = new DrinksDaoImpl(connection);

        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM drinks");
        }
    }

    @Test
    public void insertDrinks() {
        drinksDao.save(new Drink(0, "Espresso", "Эспрессо", BigDecimal.valueOf(2.50)));
        drinksDao.save(new Drink(0, "Cappuccino", "Капучино", BigDecimal.valueOf(3.00)));
        drinksDao.save(new Drink(0, "Latte", "Латте", BigDecimal.valueOf(3.50)));
        drinksDao.save(new Drink(0, "Mocha", "Мокачино", BigDecimal.valueOf(3.75)));
        drinksDao.save(new Drink(0, "Americano", "Американо", BigDecimal.valueOf(2.00)));


        List<Drink> drinks = drinksDao.findAll();
        assertEquals(5, drinks.size());
    }
}
