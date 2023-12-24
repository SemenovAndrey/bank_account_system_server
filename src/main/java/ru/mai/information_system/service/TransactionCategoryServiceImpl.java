package ru.mai.information_system.service;

import ru.mai.information_system.dao.TransactionCategoryDAO;
import ru.mai.information_system.dao.TransactionCategoryDAOImpl;
import ru.mai.information_system.entity.TransactionCategory;

import java.util.List;

public class TransactionCategoryServiceImpl implements TransactionCategoryService {

    private final TransactionCategoryDAO transactionCategoryDAO = new TransactionCategoryDAOImpl();

    @Override
    public List<TransactionCategory> getAllTransactionCategories() {
        return transactionCategoryDAO.getAllTransactionCategories();
    }

    @Override
    public TransactionCategory getTransactionCategoryById(int id) {
        return transactionCategoryDAO.getTransactionCategoryById(id);
    }

    @Override
    public List<TransactionCategory> getTransactionCategoryByUserId(int userId) {
        return transactionCategoryDAO.getTransactionCategoriesByUserId(userId);
    }

    @Override
    public TransactionCategory getTransactionCategoryByUserIdAndCategory(int userId, String category) {
        return transactionCategoryDAO.getTransactionCategoryByUserIdAndCategory(userId, category);
    }

    @Override
    public void saveTransactionCategory(TransactionCategory transactionCategory) {
        transactionCategoryDAO.saveTransactionCategory(transactionCategory);
    }

    @Override
    public void deleteTransactionCategoryById(int id) {
        transactionCategoryDAO.deleteTransactionCategoryById(id);
    }
}
