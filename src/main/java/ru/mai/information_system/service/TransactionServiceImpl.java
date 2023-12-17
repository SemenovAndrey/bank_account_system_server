package ru.mai.information_system.service;

import ru.mai.information_system.dao.TransactionDAO;
import ru.mai.information_system.dao.TransactionDAOImpl;
import ru.mai.information_system.entity.Transaction;

import java.util.List;

public class TransactionServiceImpl implements TransactionService {

    private final TransactionDAO transactionDAO = new TransactionDAOImpl();

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionDAO.getAllTransactions();
    }

    @Override
    public Transaction getTransactionById(int id) {
        return transactionDAO.getTransactionById(id);
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        transactionDAO.saveTransaction(transaction);
    }

    @Override
    public void deleteTransactionById(int id) {
        transactionDAO.deleteTransactionById(id);
    }
}
