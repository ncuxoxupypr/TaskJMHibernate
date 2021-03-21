package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/testDB&useSSL=false";
    private static final String DB_NAME = "name";
    private static final String DB_PASSWORD = "password";

    private static SessionFactory sessionFactory = createSessionFactory();

    public Connection getConnectionJDBC() {
        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL,DB_NAME,DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static SessionFactory createSessionFactory() {
        if (sessionFactory == null) {
            try {
                Properties prop = new Properties();
                prop.setProperty("dialect", "org.hibernate.dialect.MySQL8Dialect");
                prop.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
                prop.setProperty("hibernate.connection.url","jdbc:mysql://localhost:3306/db_jm?serverTimezone=Europe/Moscow");
                prop.setProperty("hibernate.connection.username","root");
                prop.setProperty("hibernate.connection.password", "password");
                prop.setProperty("show_sql", "true");
                prop.setProperty("hbm2ddl.auto", "create");
                sessionFactory = new Configuration()
                        .addProperties(prop)
                        .addAnnotatedClass(User.class)
                        .buildSessionFactory();
            } catch (HibernateException e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

}
