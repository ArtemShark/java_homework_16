package org.example;

import org.example.dao.clientsDAO.ClientsDaoImpl;
import org.example.model.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientDaoTest {
    private ClientsDaoImpl clientsDao;

    @BeforeEach
    public void setUp() throws Exception {
        String url = "jdbc:postgresql://localhost:5436/homework1-db";
        String user = "sa";
        String password = "admin";
        Connection connection = DriverManager.getConnection(url, user, password);
        clientsDao = new ClientsDaoImpl(connection);

        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM clients");
        }
    }

    @Test
    public void insertClients() {

        clientsDao.save(new Client(0, "Алиса Обдар", LocalDate.of(1985, 12, 15), "+0675432111", "alice@gmail.com", 10));
        clientsDao.save(new Client(0, "Ксения Козубская", LocalDate.of(1978, 4, 22), "+0987689900", "ksu@gmail.com", 15));
        clientsDao.save(new Client(0, "Никита Филипчук", LocalDate.of(1990, 7, 30), "+067548900", "nikitos@gmail.com", 5));
        clientsDao.save(new Client(0, "Юра Марченко", LocalDate.of(1982, 11, 3), "+0987779088", "yura@gmail.com", 20));
        clientsDao.save(new Client(0, "Ира Наливайко", LocalDate.of(1993, 3, 28), "+0674569811", "ira@gmail.com", 12));

        List<Client> clients = clientsDao.findAll();
        assertEquals(5, clients.size());
    }
}
