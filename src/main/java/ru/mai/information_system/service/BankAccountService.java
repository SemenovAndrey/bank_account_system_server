package ru.mai.information_system.service;

import ru.mai.information_system.entity.BankAccount;

import java.util.List;

public interface BankAccountService {

    public List<BankAccount> getAllBankAccounts();

    public BankAccount getBankAccountById(int id);

    public BankAccount getBankAccountByNameAndUserId(int userId, String bankAccountName);

    public void saveBankAccount(BankAccount bankAccount);

    public void deleteBankAccountById(int id);
}
