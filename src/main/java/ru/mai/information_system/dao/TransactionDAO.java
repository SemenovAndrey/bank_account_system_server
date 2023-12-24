package ru.mai.information_system.dao;

import ru.mai.information_system.entity.Transaction;

import java.util.List;

public interface TransactionDAO {

    public List<Transaction> getAllTransactions();

    public Transaction getTransactionById(int id);

    public List<Transaction> getTransactionsByBankAccountId(int bankAccountId);

    public void saveTransaction(Transaction transaction);

    public void deleteTransactionById(int id);
}
