package org.example;

import org.example.dao.staffDAO.StaffDaoImpl;
import org.example.model.Staff;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StaffDaoTest {
    private StaffDaoImpl staffDao;

    @BeforeEach
    public void setUp() throws Exception {
        String url = "jdbc:postgresql://localhost:5436/homework1-db";
        String user = "sa";
        String password = "admin";
        Connection connection = DriverManager.getConnection(url, user, password);
        staffDao = new StaffDaoImpl(connection);

        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM staff");
        }
    }

    @Test
    public void insertStaffMembers() {

        staffDao.save(new Staff(0, "Иван Максименко", "+0674523121", "ivan@gmail.com", "Бариста"));
        staffDao.save(new Staff(0, "Камилла Чуб", "+0985435544", "kammila@gmail.com", "Официант"));
        staffDao.save(new Staff(0, "Никита Гаращенко", "+0674367890", "nikita@gmail.com", "Кондитер"));
        staffDao.save(new Staff(0, "Дима Бурденюк", "+0678906543", "dima@gmail.com", "Кондитер"));
        staffDao.save(new Staff(0, "Кирил Царенок", "+0987076099", "kyryll@gmail.com", "Бариста"));

        List<Staff> staff = staffDao.findAll();
        assertEquals(5, staff.size());
    }
}
