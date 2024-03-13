package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Scanner;
import java.math.BigDecimal;
import java.time.*;

import org.example.dao.clientsDAO.ClientsDaoImpl;
import org.example.dao.drinkDAO.DrinksDaoImpl;
import org.example.dao.dessertDAO.DessertsDaoImpl;
import org.example.dao.orderdeatilsDAO.OrderDetailsDaoImpl;
import org.example.dao.ordersDAO.OrdersDaoImpl;
import org.example.dao.scheduleDAO.ScheduleDaoImpl;
import org.example.dao.staffDAO.StaffDaoImpl;
import org.example.model.*;

public class App {
    private static final Scanner scanner = new Scanner(System.in);
    private static Connection connection;

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5436/homework1-db";
        String user = "sa";
        String password = "admin";

        try {
            connection = DriverManager.getConnection(url, user, password);
            DrinksDaoImpl drinksDao = new DrinksDaoImpl(connection);
            DessertsDaoImpl dessertsDao = new DessertsDaoImpl(connection);
            StaffDaoImpl staffDao = new StaffDaoImpl(connection);
            ScheduleDaoImpl scheduleDao = new ScheduleDaoImpl(connection);
            ClientsDaoImpl clientsDao = new ClientsDaoImpl(connection);
            OrdersDaoImpl ordersDao = new OrdersDaoImpl(connection);
            OrderDetailsDaoImpl orderDetailsDao = new OrderDetailsDaoImpl(connection);

            while (true) {
                System.out.println("\nВыберите категорию:");
                System.out.println("1. Управление напитками");
                System.out.println("2. Управление десертами");
                System.out.println("3. Управление сотрудниками");
                System.out.println("4. Управление клиентами");
                System.out.println("5. Управление расписанием");
                System.out.println("6. Управление заказом");
                System.out.println("7. Выход");
                System.out.print("Ваш выбор: ");

                int category = scanner.nextInt();
                scanner.nextLine();

                switch (category) {
                    case 1:
                        manageDrinks(drinksDao);
                        break;
                    case 2:
                        manageDesserts(dessertsDao);
                        break;
                    case 3:
                        manageStaff(staffDao);
                        break;
                    case 4:
                        manageClients(clientsDao);
                        break;
                    case 5:
                        manageSchedule(scheduleDao);
                        break;
                    case 6:
                        manageOrders(ordersDao, orderDetailsDao);
                        break;
                    case 7:
                        System.out.println("Выход из программы...");
                        return;
                    default:
                        System.out.println("Неверный ввод, попробуйте еще раз.");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void manageDrinks(DrinksDaoImpl drinksDao) {
        while (true) {
            System.out.println("\nВыберите действие для таблицы 'Drinks':");
            System.out.println("1. Показать все напитки");
            System.out.println("2. Добавить новый напиток");
            System.out.println("3. Изменить напиток");
            System.out.println("4. Удалить напиток");
            System.out.println("5. Выход");
            System.out.print("Ваш выбор: ");

            int action = scanner.nextInt();
            scanner.nextLine();

            switch (action) {
                case 1:
                    showAllDrinks(drinksDao);
                    break;
                case 2:
                    addNewDrink(drinksDao);
                    break;
                case 3:
                    updateDrink(drinksDao);
                    break;
                case 4:
                    deleteDrink(drinksDao);
                    break;
                case 5:
                    System.out.println("Выход из программы...");
                    return;
                default:
                    System.out.println("Неверный ввод, попробуйте еще раз.");
                    break;
            }
        }
    }

    private static void showAllDrinks(DrinksDaoImpl drinksDao) {
        List<Drink> drinks = drinksDao.findAll();
        if (drinks.isEmpty()) {
            System.out.println("Список напитков пуст.");
        } else {
            for (Drink drink : drinks) {
                System.out.println(drink.getDrinkId() + ": " + drink.getNameEN() + " / " + drink.getNameOtherLanguage() + " - " + drink.getPrice());
            }
        }
    }

    private static void addNewDrink(DrinksDaoImpl drinksDao) {
        System.out.println("Введите название напитка на английском:");
        String nameEN = scanner.nextLine();
        System.out.println("Введите название напитка на другом языке:");
        String nameOtherLanguage = scanner.nextLine();
        System.out.println("Введите цену напитка:");
        BigDecimal price = scanner.nextBigDecimal();
        Drink drink = new Drink(0, nameEN, nameOtherLanguage, price);
        drinksDao.save(drink);
        System.out.println("Напиток добавлен.");
    }

    private static void updateDrink(DrinksDaoImpl drinksDao) {
        System.out.println("Введите ID напитка, который нужно обновить:");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Введите новое название напитка на английском:");
        String nameEN = scanner.nextLine();
        System.out.println("Введите новое название напитка на другом языке:");
        String nameOtherLanguage = scanner.nextLine();
        System.out.println("Введите новую цену напитка:");
        BigDecimal price = scanner.nextBigDecimal();
        Drink drink = new Drink(id, nameEN, nameOtherLanguage, price);
        drinksDao.update(drink);
        System.out.println("Напиток обновлен.");
    }

    private static void deleteDrink(DrinksDaoImpl drinksDao) {
        System.out.println("Введите ID напитка, который нужно удалить:");
        int id = scanner.nextInt();
        drinksDao.delete(id);
        System.out.println("Напиток удален.");
    }

    private static void manageDesserts(DessertsDaoImpl dessertsDao) {
        while (true) {
            System.out.println("\nУправление десертами:");
            System.out.println("1. Показать все десерты");
            System.out.println("2. Добавить новый десерт");
            System.out.println("3. Изменить десерт");
            System.out.println("4. Удалить десерт");
            System.out.println("5. Вернуться в предыдущее меню");
            System.out.print("Ваш выбор: ");

            int action = scanner.nextInt();
            scanner.nextLine();

            switch (action) {
                case 1:
                    showAllDesserts(dessertsDao);
                    break;
                case 2:
                    addNewDessert(dessertsDao);
                    break;
                case 3:
                    updateDessert(dessertsDao);
                    break;
                case 4:
                    deleteDessert(dessertsDao);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Неверный ввод, попробуйте еще раз.");
                    break;
            }
        }
    }

    private static void showAllDesserts(DessertsDaoImpl dessertsDao) {
        List<Dessert> desserts = dessertsDao.findAll();
        if (desserts.isEmpty()) {
            System.out.println("Список десертов пуст.");
        } else {
            for (Dessert dessert : desserts) {
                System.out.println(dessert.getDessertId() + ": " + dessert.getNameEN() + " / " + dessert.getNameOtherLanguage() + " - " + dessert.getPrice());
            }
        }
    }

    private static void addNewDessert(DessertsDaoImpl dessertsDao) {
        System.out.println("Введите название десерта на английском:");
        String nameEN = scanner.nextLine();
        System.out.println("Введите название десерта на другом языке:");
        String nameOtherLanguage = scanner.nextLine();
        System.out.println("Введите цену десерта:");
        BigDecimal price = scanner.nextBigDecimal();
        Dessert dessert = new Dessert(0, nameEN, nameOtherLanguage, price);
        dessertsDao.save(dessert);
        System.out.println("Десерт добавлен.");
    }

    private static void updateDessert(DessertsDaoImpl dessertsDao) {
        System.out.println("Введите ID десерта, который нужно обновить:");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Введите новое название десерта на английском:");
        String nameEN = scanner.nextLine();
        System.out.println("Введите новое название десерта на другом языке:");
        String nameOtherLanguage = scanner.nextLine();
        System.out.println("Введите новую цену десерта:");
        BigDecimal price = scanner.nextBigDecimal();
        Dessert dessert = new Dessert(id, nameEN, nameOtherLanguage, price);
        dessertsDao.update(dessert);
        System.out.println("Десерт обновлен.");
    }

    private static void deleteDessert(DessertsDaoImpl dessertsDao) {
        System.out.println("Введите ID десерта, который нужно удалить:");
        int id = scanner.nextInt();
        dessertsDao.delete(id);
        System.out.println("Десерт удален.");
    }

    private static void manageStaff(StaffDaoImpl staffDao) {
        while (true) {
            System.out.println("\nУправление персоналом:");
            System.out.println("1. Показать весь персонал");
            System.out.println("2. Добавить нового сотрудника");
            System.out.println("3. Изменить информацию сотрудника");
            System.out.println("4. Удалить сотрудника");
            System.out.println("5. Вернуться в предыдущее меню");
            System.out.print("Ваш выбор: ");

            int action = scanner.nextInt();
            scanner.nextLine();

            switch (action) {
                case 1:
                    showAllStaff(staffDao);
                    break;
                case 2:
                    addNewStaff(staffDao);
                    break;
                case 3:
                    updateStaff(staffDao);
                    break;
                case 4:
                    deleteStaff(staffDao);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Неверный ввод, попробуйте еще раз.");
                    break;
            }
        }
    }

    private static void showAllStaff(StaffDaoImpl staffDao) {
        List<Staff> staffList = staffDao.findAll();
        if (staffList.isEmpty()) {
            System.out.println("Список персонала пуст.");
        } else {
            for (Staff staff : staffList) {
                System.out.println(staff.getStaffId() + ": " + staff.getFullName() + ", " + staff.getPosition());
            }
        }
    }

    private static void addNewStaff(StaffDaoImpl staffDao) {
        System.out.println("Добавление нового сотрудника:");
        System.out.print("Введите полное имя: ");
        String fullName = scanner.nextLine();
        System.out.print("Введите телефон: ");
        String phone = scanner.nextLine();
        System.out.print("Введите email: ");
        String email = scanner.nextLine();
        System.out.print("Введите позицию: ");
        String position = scanner.nextLine();

        Staff staff = new Staff(0, fullName, phone, email, position);
        staffDao.save(staff);
        System.out.println("Новый сотрудник успешно добавлен.");
    }

    private static void updateStaff(StaffDaoImpl staffDao) {
        System.out.print("Введите ID сотрудника, которого хотите обновить: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Обновление сотрудника ID: " + id);
        System.out.print("Введите новое полное имя: ");
        String fullName = scanner.nextLine();
        System.out.print("Введите новый телефон: ");
        String phone = scanner.nextLine();
        System.out.print("Введите новый email: ");
        String email = scanner.nextLine();
        System.out.print("Введите новую позицию: ");
        String position = scanner.nextLine();

        Staff staff = new Staff(id, fullName, phone, email, position);
        staffDao.update(staff);
        System.out.println("Информация о сотруднике успешно обновлена.");
    }

    private static void deleteStaff(StaffDaoImpl staffDao) {
        System.out.print("Введите ID сотрудника, которого хотите удалить: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        staffDao.delete(id);
        System.out.println("Сотрудник успешно удален.");
    }

    private static void manageClients(ClientsDaoImpl clientsDao) {
        while (true) {
            System.out.println("\nУправление клиентами:");
            System.out.println("1. Показать всех клиентов");
            System.out.println("2. Добавить нового клиента");
            System.out.println("3. Изменить информацию клиента");
            System.out.println("4. Удалить клиента");
            System.out.println("5. Вернуться в предыдущее меню");
            System.out.print("Ваш выбор: ");

            int action = scanner.nextInt();
            scanner.nextLine();

            switch (action) {
                case 1:
                    showAllClients(clientsDao);
                    break;
                case 2:
                    addNewClient(clientsDao);
                    break;
                case 3:
                    updateClient(clientsDao);
                    break;
                case 4:
                    deleteClient(clientsDao);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Неверный ввод, попробуйте еще раз.");
                    break;
            }
        }
    }
    private static void showAllClients(ClientsDaoImpl clientsDao) {
        List<Client> clientsList = clientsDao.findAll();
        if (clientsList.isEmpty()) {
            System.out.println("Список клиентов пуст.");
        } else {
            for (Client client : clientsList) {
                System.out.println(client.getClientId() + ": " + client.getFullName() + ", Email: " + client.getEmail() + ", Телефон: " + client.getPhone() + ", Скидка: " + client.getDiscount() + "%");
            }
        }
    }

    private static void addNewClient(ClientsDaoImpl clientsDao) {
        System.out.println("Добавление нового клиента:");
        System.out.print("Введите полное имя: ");
        String fullName = scanner.nextLine();
        System.out.print("Введите дату рождения (ГГГГ-ММ-ДД): ");
        String birthDate = scanner.nextLine();
        System.out.print("Введите телефон: ");
        String phone = scanner.nextLine();
        System.out.print("Введите email: ");
        String email = scanner.nextLine();
        System.out.print("Введите процент скидки: ");
        int discount = scanner.nextInt();
        scanner.nextLine();

        Client client = new Client(0, fullName, LocalDate.parse(birthDate), phone, email, discount);
        clientsDao.save(client);
        System.out.println("Новый клиент успешно добавлен.");
    }

    private static void updateClient(ClientsDaoImpl clientsDao) {
        System.out.print("Введите ID клиента, которого хотите обновить: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Обновление клиента ID: " + id);
        System.out.print("Введите новое полное имя: ");
        String fullName = scanner.nextLine();
        System.out.print("Введите новую дату рождения (ГГГГ-ММ-ДД): ");
        String birthDate = scanner.nextLine();
        System.out.print("Введите новый телефон: ");
        String phone = scanner.nextLine();
        System.out.print("Введите новый email: ");
        String email = scanner.nextLine();
        System.out.print("Введите новый процент скидки: ");
        int discount = scanner.nextInt();
        scanner.nextLine();

        Client client = new Client(id, fullName, LocalDate.parse(birthDate), phone, email, discount);
        clientsDao.update(client);
        System.out.println("Информация о клиенте успешно обновлена.");
    }

    private static void deleteClient(ClientsDaoImpl clientsDao) {
        System.out.print("Введите ID клиента, которого хотите удалить: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        clientsDao.delete(id);
        System.out.println("Клиент успешно удален.");
    }

    private static void manageSchedule(ScheduleDaoImpl scheduleDao) {
        while (true) {
            System.out.println("\nУправление расписанием:");
            System.out.println("1. Показать всё расписание");
            System.out.println("2. Добавить запись в расписание");
            System.out.println("3. Изменить запись в расписание");
            System.out.println("4. Удалить запись из расписания");
            System.out.println("5. Вернуться в предыдущее меню");
            System.out.print("Ваш выбор: ");

            int action = scanner.nextInt();
            scanner.nextLine();

            switch (action) {
                case 1:
                    showAllSchedules(scheduleDao);
                    break;
                case 2:
                    addNewSchedule(scheduleDao);
                    break;
                case 3:
                    updateSchedule(scheduleDao);
                    break;
                case 4:
                    deleteSchedule(scheduleDao);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Неверный ввод, попробуйте еще раз.");
                    break;
            }
        }
    }

    private static void showAllSchedules(ScheduleDaoImpl scheduleDao) {
        List<Schedule> scheduleList = scheduleDao.findAll();
        if (scheduleList.isEmpty()) {
            System.out.println("Расписание пусто.");
        } else {
            for (Schedule schedule : scheduleList) {
                System.out.println("ID: " + schedule.getScheduleId() + ", ID Сотрудника: " + schedule.getStaffId() + ", Дата: " + schedule.getWorkDate() + ", Начало: " + schedule.getStartTime() + ", Конец: " + schedule.getEndTime());
            }
        }
    }

    private static void addNewSchedule(ScheduleDaoImpl scheduleDao) {
        System.out.println("Добавление новой записи в расписание:");
        System.out.print("Введите ID сотрудника: ");
        int staffId = scanner.nextInt();
        System.out.print("Введите дату работы (ГГГГ-ММ-ДД): ");
        String workDate = scanner.next();
        System.out.print("Введите время начала (ЧЧ:ММ): ");
        String startTime = scanner.next();
        System.out.print("Введите время окончания (ЧЧ:ММ): ");
        String endTime = scanner.next();

        Schedule schedule = new Schedule(0, staffId, LocalDate.parse(workDate), LocalTime.parse(startTime), LocalTime.parse(endTime));
        scheduleDao.save(schedule);
        System.out.println("Запись в расписание успешно добавлена.");
    }

    private static void updateSchedule(ScheduleDaoImpl scheduleDao) {
        System.out.print("Введите ID записи в расписании, которую хотите обновить: ");
        int scheduleId = scanner.nextInt();
        System.out.print("Введите новый ID сотрудника: ");
        int staffId = scanner.nextInt();
        System.out.print("Введите новую дату работы (ГГГГ-ММ-ДД): ");
        String workDate = scanner.next();
        System.out.print("Введите новое время начала (ЧЧ:ММ): ");
        String startTime = scanner.next();
        System.out.print("Введите новое время окончания (ЧЧ:ММ): ");
        String endTime = scanner.next();

        Schedule schedule = new Schedule(scheduleId, staffId, LocalDate.parse(workDate), LocalTime.parse(startTime), LocalTime.parse(endTime));
        scheduleDao.update(schedule);
        System.out.println("Запись в расписание успешно обновлена.");
    }

    private static void deleteSchedule(ScheduleDaoImpl scheduleDao) {
        System.out.print("Введите ID записи в расписании, которую хотите удалить: ");
        int scheduleId = scanner.nextInt();

        scheduleDao.delete(scheduleId);
        System.out.println("Запись в расписание успешно удалена.");
    }


    private static void manageOrders(OrdersDaoImpl ordersDao, OrderDetailsDaoImpl orderDetailsDao) {
        while (true) {
            System.out.println("\nУправление заказами:");
            System.out.println("1. Показать все заказы");
            System.out.println("2. Добавить новый заказ");
            System.out.println("3. Изменить заказ");
            System.out.println("4. Удалить заказ");
            System.out.println("5. Управление деталями заказа");
            System.out.println("6. Вернуться в предыдущее меню");
            System.out.print("Ваш выбор: ");

            int action = scanner.nextInt();
            scanner.nextLine();

            switch (action) {
                case 1:
                    showAllOrders(ordersDao);
                    break;
                case 2:
                    addNewOrder(ordersDao);
                    break;
                case 3:
                    updateOrder(ordersDao);
                    break;
                case 4:
                    deleteOrder(ordersDao);
                    break;
                case 5:
                    manageOrderDetails(orderDetailsDao);
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Неверный ввод, попробуйте еще раз.");
                    break;
            }
        }
    }

    private static void showAllOrders(OrdersDaoImpl ordersDao) {
        List<Order> orders = ordersDao.findAll();
        if (orders.isEmpty()) {
            System.out.println("Список заказов пуст.");
        } else {
            for (Order order : orders) {
                System.out.println("Заказ ID: " + order.getOrderId() + ", Клиент ID: " + order.getClientId() + ", Сотрудник ID: " + order.getStaffId() + ", Дата заказа: " + order.getOrderDate() + ", Общая стоимость: " + order.getTotalPrice());
            }
        }
    }

    private static void addNewOrder(OrdersDaoImpl ordersDao) {
        System.out.println("Добавление нового заказа:");
        System.out.print("Введите ID клиента: ");
        int clientId = scanner.nextInt();
        System.out.print("Введите ID сотрудника: ");
        int staffId = scanner.nextInt();
        System.out.print("Введите общую стоимость заказа: ");
        BigDecimal totalPrice = scanner.nextBigDecimal();

        LocalDate orderDate = LocalDate.now();

        Order order = new Order(0, clientId, staffId, orderDate, totalPrice);
        ordersDao.save(order);
        System.out.println("Новый заказ успешно добавлен.");
    }

    private static void updateOrder(OrdersDaoImpl ordersDao) {
        System.out.print("Введите ID заказа, который хотите обновить: ");
        int orderId = scanner.nextInt();
        System.out.print("Введите новый ID клиента: ");
        int clientId = scanner.nextInt();
        System.out.print("Введите новый ID сотрудника: ");
        int staffId = scanner.nextInt();
        System.out.print("Введите новую общую стоимость заказа: ");
        BigDecimal totalPrice = scanner.nextBigDecimal();

        Order order = new Order(orderId, clientId, staffId, LocalDate.now(), totalPrice);
        System.out.println("Заказ успешно обновлен.");
    }


    private static void deleteOrder(OrdersDaoImpl ordersDao) {
        System.out.print("Введите ID заказа, который хотите удалить: ");
        int orderId = scanner.nextInt();
        ordersDao.delete(orderId);
        System.out.println("Заказ успешно удален.");
    }


    private static void manageOrderDetails(OrderDetailsDaoImpl orderDetailsDao) {
        while (true) {
            System.out.println("\nУправление деталями заказа:");
            System.out.println("1. Показать все детали заказов");
            System.out.println("2. Добавить деталь к заказу");
            System.out.println("3. Изменить деталь заказа");
            System.out.println("4. Удалить деталь из заказа");
            System.out.println("5. Вернуться в предыдущее меню");
            System.out.print("Ваш выбор: ");

            int action = scanner.nextInt();
            scanner.nextLine();

            switch (action) {
                case 1:
                    showAllOrderDetails(orderDetailsDao);
                    break;
                case 2:
                    addNewOrderDetail(orderDetailsDao);
                    break;
                case 3:
                    updateOrderDetail(orderDetailsDao);
                    break;
                case 4:
                    deleteOrderDetail(orderDetailsDao);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Неверный ввод, попробуйте еще раз.");
                    break;
            }
        }
    }

    private static void showAllOrderDetails(OrderDetailsDaoImpl orderDetailsDao) {
        List<OrderDetail> orderDetails = orderDetailsDao.findAll();
        if (orderDetails.isEmpty()) {
            System.out.println("Список деталей заказов пуст.");
        } else {
            for (OrderDetail detail : orderDetails) {
                System.out.println("Деталь заказа ID: " + detail.getOrderDetailId() + ", Заказ ID: " + detail.getOrderId() +
                        ", Тип: " + detail.getItemType() + ", ID товара: " + detail.getItemId() + ", Количество: " + detail.getQuantity());
            }
        }
    }


    private static void addNewOrderDetail(OrderDetailsDaoImpl orderDetailsDao) {
        System.out.println("Добавление детали к заказу:");
        System.out.print("Введите ID заказа: ");
        int orderId = scanner.nextInt();
        System.out.print("Введите тип детали (например, Drink или Dessert): ");
        String itemType = scanner.next();
        System.out.print("Введите ID товара: ");
        int itemId = scanner.nextInt();
        System.out.print("Введите количество: ");
        int quantity = scanner.nextInt();

        OrderDetail orderDetail = new OrderDetail(0, orderId, itemType, itemId, quantity);
        orderDetailsDao.save(orderDetail);
        System.out.println("Деталь успешно добавлена к заказу.");
    }

    private static void updateOrderDetail(OrderDetailsDaoImpl orderDetailsDao) {
        System.out.print("Введите ID детали заказа, которую хотите обновить: ");
        int orderDetailId = scanner.nextInt();
        System.out.print("Введите новый ID заказа: ");
        int orderId = scanner.nextInt();
        System.out.print("Введите новый тип детали (например, Drink или Dessert): ");
        String itemType = scanner.next();
        System.out.print("Введите новый ID товара: ");
        int itemId = scanner.nextInt();
        System.out.print("Введите новое количество: ");
        int quantity = scanner.nextInt();

        OrderDetail orderDetail = new OrderDetail(orderDetailId, orderId, itemType, itemId, quantity);
        orderDetailsDao.update(orderDetail);
        System.out.println("Деталь заказа успешно обновлена.");
    }

    private static void deleteOrderDetail(OrderDetailsDaoImpl orderDetailsDao) {
        System.out.print("Введите ID детали заказа, которую хотите удалить: ");
        int orderDetailId = scanner.nextInt();

        orderDetailsDao.delete(orderDetailId);
        System.out.println("Деталь заказа успешно удалена.");
    }
}
