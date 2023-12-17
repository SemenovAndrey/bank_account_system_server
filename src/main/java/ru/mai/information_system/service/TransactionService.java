package ru.mai.information_system.service;

import ru.mai.information_system.entity.Transaction;

import java.util.List;

public interface TransactionService {

    public List<Transaction> getAllTransactions();

    public Transaction getTransactionById(int id);

    public void saveTransaction(Transaction user);

    public void deleteTransactionById(int id);
}
