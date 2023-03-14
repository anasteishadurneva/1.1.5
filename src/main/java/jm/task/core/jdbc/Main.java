package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;

public class Main {
    private final static UserDaoHibernateImpl userService = new UserDaoHibernateImpl();

    public static void main(String[] args) {
        userService.createUsersTable();

        userService.saveUser("Эмили", "Браун", (byte) 32);
        userService.saveUser("Максим", "Кузнецов", (byte) 27);
        userService.saveUser("Анна", "Смирнова", (byte) 41);
        userService.saveUser("Иван", "Петров", (byte) 56);

        userService.removeUserById(2);

        userService.getAllUsers();

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}
