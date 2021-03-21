package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {

    private Connection connection = getConnectionJDBC();
    private Statement statement = null;

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String sql = "CREATE TABLE users" +
                "(id int NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                "name char(45) NOT NULL, " +
                "lastName char(45) NULL, " +
                "age int null" +
                ") ENGINE=InnoDB;";
       processingSQLQuery(sql);

    }

    public void dropUsersTable() {
        String sql = "DROP TABLE users";
        processingSQLQuery(sql);
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO `users` (`name`, `lastname`, `age`) " +
                "VALUES ('" + name + "', '" + lastName + "', " + age + ")";
        processingSQLQuery(sql);
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id=" + id;
        processingSQLQuery(sql);
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String name = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                byte age = resultSet.getByte(4);
                list.add(new User(name, lastName, age));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM users";
        processingSQLQuery(sql);
    }

    private void processingSQLQuery(String sqlQuery) {
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
