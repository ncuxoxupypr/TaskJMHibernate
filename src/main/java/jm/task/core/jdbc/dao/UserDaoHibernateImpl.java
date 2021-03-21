package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl extends Util implements UserDao {

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = createSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String sql = "CREATE TABLE IF NOT EXISTS users(\n"
                + "id int NOT NULL AUTO_INCREMENT,\n"
                + "firstName varchar(45),\n"
                + "lastName varchar(45),\n"
                + "age int,\n"
                + "PRIMARY KEY (id))";
        session.createSQLQuery(sql).executeUpdate();
        transaction.commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = createSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
        transaction.commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = createSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(new User(name,lastName,age));
        transaction.commit();
        session.close();

    }

    @Override
    public void removeUserById(long id) {
        Session session = createSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.load(User.class, id);
        session.remove(user);
        transaction.commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = createSessionFactory().openSession();
        List<User> list = session.createQuery("From " + User.class.getSimpleName()).list();
        session.close();
        return list;
    }

    @Override
    public void cleanUsersTable() {
        Session session = createSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.createQuery("delete from User").executeUpdate();
        transaction.commit();
        session.close();
    }


}
