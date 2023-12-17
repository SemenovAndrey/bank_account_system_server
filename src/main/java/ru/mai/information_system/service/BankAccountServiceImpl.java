package ru.mai.information_system.service;

import ru.mai.information_system.dao.BankAccountDAO;
import ru.mai.information_system.dao.BankAccountDAOImpl;
import ru.mai.information_system.entity.BankAccount;

import java.util.List;

public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountDAO bankAccountDAO = new BankAccountDAOImpl();

    @Override
    public List<BankAccount> getAllBankAccounts() {
        return bankAccountDAO.getAllBankAccounts();
    }

    @Override
    public BankAccount getBankAccountById(int id) {
        return bankAccountDAO.getBankAccountById(id);
    }

    @Override
    public BankAccount getBankAccountByNameAndUserId(int userId, String bankAccountName) {
        return bankAccountDAO.getBankAccountByNameAndUserId(userId, bankAccountName);
    }

    @Override
    public void saveBankAccount(BankAccount bankAccount) {
        bankAccountDAO.saveBankAccount(bankAccount);
    }

    @Override
    public void deleteBankAccountById(int id) {
        bankAccountDAO.deleteBankAccountById(id);
    }
}
