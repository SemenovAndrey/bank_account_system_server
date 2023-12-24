package ru.mai.information_system.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.mai.information_system.Server;
import ru.mai.information_system.entity.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionDAOImpl implements TransactionDAO {

    private final SessionFactory SESSION_FACTORY = Server.getSessionFactory();

    @Override
    public List<Transaction> getAllTransactions() {
        Session session = null;

        List<Transaction> transactions = new ArrayList<>();
        try {
            session = SESSION_FACTORY.getCurrentSession();
            session.beginTransaction();
            transactions = session.createQuery("from Transaction", Transaction.class).getResultList();
            System.out.println("Method getAllTransactions()");
            for (Transaction user : transactions) {
                System.out.println(user);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }

        return transactions;
    }

    @Override
    public Transaction getTransactionById(int id) {
        Session session = SESSION_FACTORY.getCurrentSession();

        Transaction transaction = null;
        try {
            session.beginTransaction();
            transaction = session.get(Transaction.class, id);
            System.out.println("Method getTransactionById()");
            System.out.println(transaction);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }

        return transaction;
    }

    @Override
    public List<Transaction> getTransactionsByBankAccountId(int bankAccountId) {
        Session session = null;
        List<Transaction> transactions = new ArrayList<>();

        try {
            session = SESSION_FACTORY.getCurrentSession();
            session.beginTransaction();

            transactions = session.createQuery("from Transaction " +
                            "where bank_account_id =: bankAccountId")
                    .setParameter("bankAccountId", bankAccountId)
                    .getResultList();
            System.out.println(transactions);

            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }

        return transactions;
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        Session session = SESSION_FACTORY.getCurrentSession();

        try {
            session.beginTransaction();
            session.saveOrUpdate(transaction);
            System.out.println("Method saveTransaction()");
            System.out.println(transaction);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void deleteTransactionById(int id) {
        Session session = SESSION_FACTORY.getCurrentSession();

        try {
            session.beginTransaction();
            Transaction transaction = session.get(Transaction.class, id);
            System.out.println("Method deleteTransaction()");
            session.delete(transaction);
            System.out.println(transaction);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
    }
}
