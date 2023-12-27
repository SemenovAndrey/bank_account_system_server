package ru.mai.information_system.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.mai.information_system.Server;
import ru.mai.information_system.entity.BankAccountType;

import java.util.ArrayList;
import java.util.List;

public class BankAccountTypeDAOImpl implements BankAccountTypeDAO {

    private final SessionFactory SESSION_FACTORY = Server.getSessionFactory();

    @Override
    public List<BankAccountType> getAllBankAccountTypes() {
        Session session = null;

        List<BankAccountType> bankAccountTypes = new ArrayList<>();
        try {
            session = SESSION_FACTORY.getCurrentSession();
            session.beginTransaction();
            bankAccountTypes = session.createQuery("from BankAccountType", BankAccountType.class).getResultList();
            System.out.println("Method getAllBankAccountTypes()");
            for (BankAccountType bankAccountType : bankAccountTypes) {
                System.out.println(bankAccountType);
            }
            System.out.println();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }

        return bankAccountTypes;
    }

    @Override
    public BankAccountType getBankAccountTypeById(int id) {
        Session session = SESSION_FACTORY.getCurrentSession();

        BankAccountType bankAccountType = null;
        try {
            session.beginTransaction();
            bankAccountType = session.get(BankAccountType.class, id);
            System.out.println("Method getBankAccountTypeById()");
            System.out.println(bankAccountType + "\n");
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }

        return bankAccountType;
    }

    @Override
    public void saveBankAccountType(BankAccountType bankAccountType) {
        Session session = SESSION_FACTORY.getCurrentSession();

        try {
            session.beginTransaction();
            session.saveOrUpdate(bankAccountType);
            System.out.println("Method saveBankAccountType()");
            System.out.println(bankAccountType + "\n");
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void deleteBankAccountTypeById(int id) {
        Session session = SESSION_FACTORY.getCurrentSession();

        try {
            session.beginTransaction();
            BankAccountType bankAccountType = session.get(BankAccountType.class, id);
            System.out.println("Method deleteBankAccountTypeById()");
            session.delete(bankAccountType);
            System.out.println(bankAccountType + "\n");
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
    }
}
