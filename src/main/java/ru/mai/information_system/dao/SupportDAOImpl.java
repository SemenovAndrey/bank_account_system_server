package ru.mai.information_system.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.mai.information_system.Server;
import ru.mai.information_system.entity.Support;

import java.util.ArrayList;
import java.util.List;

public class SupportDAOImpl implements SupportDAO {

    private final SessionFactory SESSION_FACTORY = Server.getSessionFactory();

    @Override
    public List<Support> getAllSupports() {
        Session session = null;

        List<Support> supports = new ArrayList<>();
        try {
            session = SESSION_FACTORY.getCurrentSession();
            session.beginTransaction();
            supports = session.createQuery("from Support", Support.class).getResultList();
            System.out.println("Method getAllSupports()");
            for (Support support : supports) {
                System.out.println(support);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }

        return supports;
    }

    @Override
    public Support getSupportById(int id) {
        Session session = SESSION_FACTORY.getCurrentSession();

        Support support = null;
        try {
            session.beginTransaction();
            support = session.get(Support.class, id);
            System.out.println("Method getSupportById()");
            System.out.println(support);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }

        return support;
    }

    @Override
    public void saveSupport(Support support) {
        Session session = SESSION_FACTORY.getCurrentSession();

        try {
            session.beginTransaction();
            session.saveOrUpdate(support);
            System.out.println("Method saveSupport()");
            System.out.println(support);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void deleteSupportById(int id) {
        Session session = SESSION_FACTORY.getCurrentSession();

        try {
            session.beginTransaction();
            Support support = session.get(Support.class, id);
            System.out.println("Method deleteSupportById()");
            session.delete(support);
            System.out.println(support);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
    }
}
