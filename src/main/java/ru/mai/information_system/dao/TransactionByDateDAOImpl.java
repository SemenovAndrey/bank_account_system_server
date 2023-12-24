package ru.mai.information_system.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.mai.information_system.Server;
import ru.mai.information_system.entity.TransactionByDate;

import java.util.ArrayList;
import java.util.List;

public class TransactionByDateDAOImpl implements TransactionByDateDAO {

    private final SessionFactory SESSION_FACTORY = Server.getSessionFactory();

    @Override
    public List<TransactionByDate> getAllTransactionsByDate() {
        Session session = null;

        List<TransactionByDate> transactionByDateList = new ArrayList<>();
        try {
            session = SESSION_FACTORY.getCurrentSession();
            session.beginTransaction();
            transactionByDateList = session.createQuery("from TransactionByDate ", TransactionByDate.class).getResultList();
            System.out.println("Method getAllTransactionsBy()");
            for (TransactionByDate transactionByDate : transactionByDateList) {
                System.out.println(transactionByDate);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }

        return transactionByDateList;
    }

    @Override
    public TransactionByDate getTransactionByDateById(int id) {
        Session session = SESSION_FACTORY.getCurrentSession();

        TransactionByDate transactionByDate = null;
        try {
            session.beginTransaction();
            transactionByDate = session.get(TransactionByDate.class, id);
            System.out.println("Method getTransactionByDateById()");
            System.out.println(transactionByDate);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }

        return transactionByDate;
    }

    @Override
    public List<TransactionByDate> getTransactionsByDateByBankAccountId(int bankAccountId) {
        Session session = null;
        List<TransactionByDate> transactionsByDate = new ArrayList<>();

        try {
            session = SESSION_FACTORY.getCurrentSession();
            session.beginTransaction();

            transactionsByDate = session.createQuery("from TransactionByDate " +
                    "where bank_account_id =: bankAccountId")
                    .setParameter("bankAccountId", bankAccountId)
                    .getResultList();
            System.out.println(transactionsByDate);

            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }

        return transactionsByDate;
    }

    @Override
    public void saveTransactionByDate(TransactionByDate transactionByDate) {
        Session session = SESSION_FACTORY.getCurrentSession();

        try {
            session.beginTransaction();
            session.saveOrUpdate(transactionByDate);
            System.out.println("Method saveTransactionByDate()");
            System.out.println(transactionByDate);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void deleteTransactionByDateById(int id) {
        Session session = SESSION_FACTORY.getCurrentSession();

        try {
            session.beginTransaction();
            TransactionByDate transactionByDate = session.get(TransactionByDate.class, id);
            System.out.println("Method deleteTransactionByDateById()");
            session.delete(transactionByDate);
            System.out.println(transactionByDate);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
    }
}
