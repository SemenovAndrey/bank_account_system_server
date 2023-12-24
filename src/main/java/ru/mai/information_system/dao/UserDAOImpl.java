package ru.mai.information_system.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.mai.information_system.Server;
import ru.mai.information_system.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private final SessionFactory SESSION_FACTORY = Server.getSessionFactory();

    @Override
    public List<User> getAllUsers() {
        Session session = null;

        List<User> users = new ArrayList<>();
        try {
            session = SESSION_FACTORY.getCurrentSession();
            session.beginTransaction();
            users = session.createQuery("from User", User.class).getResultList();
            System.out.println("Method getAllUsers()");
            for (User user : users) {
                System.out.println(user);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }

        return users;
    }

    @Override
    public User getUserById(int id) {
        Session session = SESSION_FACTORY.getCurrentSession();

        User user = null;
        try {
            session.beginTransaction();
            user = session.get(User.class, id);
            System.out.println("Method getUserById()");
            System.out.println(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }

        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        Session session = null;
        User user = null;

        try {
            session = SESSION_FACTORY.getCurrentSession();
            session.beginTransaction();

            user = session.createQuery("from User where email =: email", User.class)
                    .setParameter("email", email).getSingleResult();
            System.out.println(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }

        return user;
    }


    @Override
    public void saveUser(User user) {
        Session session = SESSION_FACTORY.getCurrentSession();

        try {
            session.beginTransaction();
            session.saveOrUpdate(user);
            System.out.println("Method saveUser()");
            System.out.println(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void deleteUserById(int id) {
        Session session = SESSION_FACTORY.getCurrentSession();

        try {
            session.beginTransaction();
            User user = session.get(User.class, id);
            System.out.println("Method deleteUser()");
            session.delete(user);
            System.out.println(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
    }
}
