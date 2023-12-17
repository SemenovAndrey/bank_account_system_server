package ru.mai.information_system.dao;

import ru.mai.information_system.entity.TransactionByDate;

import java.util.List;

public interface TransactionByDateDAO {

    public List<TransactionByDate> getAllTransactionsByDate();

    public TransactionByDate getTransactionByDateById(int id);

    public void saveTransactionByDate(TransactionByDate transactionByDate);

    public void deleteTransactionByDateById(int id);
}
