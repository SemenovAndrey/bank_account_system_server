package ru.mai.information_system.service;

import ru.mai.information_system.dao.TransactionByDateDAO;
import ru.mai.information_system.dao.TransactionByDateDAOImpl;
import ru.mai.information_system.entity.TransactionByDate;

import java.util.List;

public class TransactionByDateServiceImpl implements TransactionByDateService {

    private final TransactionByDateDAO transactionByDateDAO = new TransactionByDateDAOImpl();

    @Override
    public List<TransactionByDate> getAllTransactionsByDate() {
        return transactionByDateDAO.getAllTransactionsByDate();
    }

    @Override
    public TransactionByDate getTransactionByDateById(int id) {
        return transactionByDateDAO.getTransactionByDateById(id);
    }

    @Override
    public List<TransactionByDate> getTransactionsByDateByBankAccountId(int bankAccountId) {
        return transactionByDateDAO.getTransactionsByDateByBankAccountId(bankAccountId);
    }

    @Override
    public void saveTransactionByDate(TransactionByDate transactionByDate) {
        transactionByDateDAO.saveTransactionByDate(transactionByDate);
    }

    @Override
    public void deleteTransactionByDateById(int id) {
        transactionByDateDAO.deleteTransactionByDateById(id);
    }
}
