package ru.mai.information_system.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.mai.information_system.Server;
import ru.mai.information_system.entity.BankAccount;

import java.util.ArrayList;
import java.util.List;

public class BankAccountDAOImpl implements BankAccountDAO {

    private final SessionFactory SESSION_FACTORY = Server.getSessionFactory();

    @Override
    public List<BankAccount> getAllBankAccounts() {
        Session session = null;

        List<BankAccount> bankAccounts = new ArrayList<>();
        try {
            session = SESSION_FACTORY.getCurrentSession();
            session.beginTransaction();
            bankAccounts = session.createQuery("from BankAccount", BankAccount.class).getResultList();
            System.out.println("Method getAllBankAccounts()");
            for (BankAccount bankAccount : bankAccounts) {
                System.out.println(bankAccount);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }

        return bankAccounts;
    }

    @Override
    public BankAccount getBankAccountById(int id) {
        Session session = SESSION_FACTORY.getCurrentSession();

        BankAccount bankAccount = null;
        try {
            session.beginTransaction();
            bankAccount = session.get(BankAccount.class, id);
            System.out.println("Method getBankAccountById()");
            System.out.println(bankAccount);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }

        return bankAccount;
    }

//    @Override
//    public List<BankAccount> getBankAccountByUserId(int userId) {
//        Session session = null;
//        List<BankAccount> bankAccounts;
//
//
//    }

    @Override
    public BankAccount getBankAccountByNameAndUserId(int userId, String bankAccountName) {
        Session session = null;

        BankAccount bankAccount = null;
        try {
            List<BankAccount> bankAccounts = getAllBankAccounts();
            session = SESSION_FACTORY.getCurrentSession();
            session.beginTransaction();
            for (BankAccount account : bankAccounts) {
                if (userId == account.getUser().getId() && bankAccountName.equals(account.getName())) {
                    bankAccount = account;
                    break;
                }
            }
            System.out.println("Method getBankAccountByNameAndUserId()");
            System.out.println(bankAccount);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }

        return bankAccount;
    }

    @Override
    public void saveBankAccount(BankAccount bankAccount) {
        Session session = SESSION_FACTORY.getCurrentSession();

        try {
            session.beginTransaction();
            session.saveOrUpdate(bankAccount);
            System.out.println("Method saveBankAccount()");
            System.out.println(bankAccount);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void deleteBankAccountById(int id) {
        Session session = SESSION_FACTORY.getCurrentSession();

        try {
            session.beginTransaction();
            BankAccount bankAccount = session.get(BankAccount.class, id);
            System.out.println("Method deleteBankAccountById()");
            session.delete(bankAccount);
            System.out.println(bankAccount);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
    }
}
