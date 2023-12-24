package ru.mai.information_system.dao;

import ru.mai.information_system.entity.TransactionCategory;

import java.util.List;

public interface TransactionCategoryDAO {

    public List<TransactionCategory> getAllTransactionCategories();

    public TransactionCategory getTransactionCategoryById(int id);

    public List<TransactionCategory> getTransactionCategoriesByUserId(int userId);

    public TransactionCategory getTransactionCategoryByUserIdAndCategory(int userId, String category);

    public void saveTransactionCategory(TransactionCategory transactionCategory);

    public void deleteTransactionCategoryById(int id);
}
