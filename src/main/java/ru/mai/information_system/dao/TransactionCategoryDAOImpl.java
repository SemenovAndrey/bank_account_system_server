package ru.mai.information_system.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.mai.information_system.Server;
import ru.mai.information_system.entity.TransactionCategory;

import java.util.ArrayList;
import java.util.List;

public class TransactionCategoryDAOImpl implements TransactionCategoryDAO {

    private final SessionFactory SESSION_FACTORY = Server.getSessionFactory();

    @Override
    public List<TransactionCategory> getAllTransactionCategories() {
        Session session = null;

        List<TransactionCategory> transactionCategoryList = new ArrayList<>();
        try {
            session = SESSION_FACTORY.getCurrentSession();
            session.beginTransaction();
            transactionCategoryList = session.createQuery("from TransactionCategory ", TransactionCategory.class).getResultList();
            System.out.println("Method getAllTransactionCategories()");
            for (TransactionCategory transactionCategory : transactionCategoryList) {
                System.out.println(transactionCategory);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }

        return transactionCategoryList;
    }

    @Override
    public TransactionCategory getTransactionCategoryById(int id) {
        Session session = SESSION_FACTORY.getCurrentSession();

        TransactionCategory transactionCategory = null;
        try {
            session.beginTransaction();
            transactionCategory = session.get(TransactionCategory.class, id);
            System.out.println("Method getTransactionCategoryById()");
            System.out.println(transactionCategory);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }

        return transactionCategory;
    }

    @Override
    public TransactionCategory getTransactionCategoryByUserIdAndCategory(int userId, String category) {
        Session session = null;
        TransactionCategory transactionCategory = null;
        try {
            List<TransactionCategory> transactionCategories = this.getAllTransactionCategories();
            session = SESSION_FACTORY.getCurrentSession();
            session.beginTransaction();
            for (TransactionCategory transactionCategoryCheck : transactionCategories) {
                if (userId == transactionCategoryCheck.getUser().getId() && category.equals(transactionCategoryCheck.getCategory())) {
                    transactionCategory = transactionCategoryCheck;
                    break;
                }
            }
            System.out.println("Method getTransactionCategoryByCategory()");
            System.out.println(transactionCategory);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }

        return transactionCategory;
    }

    @Override
    public void saveTransactionCategory(TransactionCategory transactionCategory) {
        Session session = SESSION_FACTORY.getCurrentSession();

        try {
            session.beginTransaction();
            session.saveOrUpdate(transactionCategory);
            System.out.println("Method saveTransactionCategory()");
            System.out.println(transactionCategory);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void deleteTransactionCategoryById(int id) {
        Session session = SESSION_FACTORY.getCurrentSession();

        try {
            session.beginTransaction();
            TransactionCategory transactionCategory = session.get(TransactionCategory.class, id);
            System.out.println("Method deleteTransactionCategory()");
            session.delete(transactionCategory);
            System.out.println(transactionCategory);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
    }
}
