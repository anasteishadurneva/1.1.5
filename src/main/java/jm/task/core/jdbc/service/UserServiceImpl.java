package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    // создание объекта UserDaoJDBCImpl для работы с БД
    UserDao userDao = new UserDaoJDBCImpl();

    // создание таблицы пользователей
    public void createUsersTable() {
        userDao.createUsersTable();
    }

    // удаление таблицы пользователей
    public void dropUsersTable() {
        userDao.dropUsersTable();
    }

    // добавление пользователя в БД
    public void saveUser(String name, String lastName, byte age) {
        userDao.saveUser(name, lastName, age);
        System.out.println("User с именем – " + name + " добавлен в базу данных");
    }

    // удаление пользователя из БД по его id
    public void removeUserById(long id) {
        userDao.removeUserById(id);
    }

    // получение списка всех пользователей из БД
    public List<User> getAllUsers() {
        List<User> users =  userDao.getAllUsers();
        // вывод списка пользователей в консоль
        for (User user : users) {
            System.out.println(user);
        }
        return users;
    }

    // очистка таблицы пользователей
    public void cleanUsersTable() {
        userDao.cleanUsersTable();
    }
}
