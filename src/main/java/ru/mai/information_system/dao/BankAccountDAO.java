package ru.mai.information_system.dao;

import ru.mai.information_system.entity.BankAccount;

import java.util.List;

public interface BankAccountDAO {

    public List<BankAccount> getAllBankAccounts();

    public BankAccount getBankAccountById(int id);

    public List<BankAccount> getBankAccountByUserId(int userId);

    public BankAccount getBankAccountByUserIdAndName(int userId, String bankAccountName);

    public void saveBankAccount(BankAccount bankAccount);

    public void deleteBankAccountById(int id);
}
