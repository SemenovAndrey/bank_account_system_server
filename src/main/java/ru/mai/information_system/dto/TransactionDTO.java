package ru.mai.information_system.dto;

import ru.mai.information_system.entity.Transaction;
import ru.mai.information_system.service.BankAccountServiceImpl;
import ru.mai.information_system.service.TransactionCategoryServiceImpl;

public class TransactionDTO {

    private int id;
    private int bankAccountId;
    private double amount;
    private int transactionCategoryId;
    private String transactionDate;
    private String comment;

    public TransactionDTO() {}

    public TransactionDTO(int id, int bankAccountId, double amount,
                                int transactionCategoryId, String transactionDate) {
        this.id = id;
        this.bankAccountId = bankAccountId;
        this.amount = amount;
        this.transactionCategoryId = transactionCategoryId;
        this.transactionDate = transactionDate;
    }

    public TransactionDTO(int id, int bankAccountId, double amount,
                                int transactionCategoryId, String transactionDate, String comment) {
        this.id = id;
        this.bankAccountId = bankAccountId;
        this.amount = amount;
        this.transactionCategoryId = transactionCategoryId;
        this.transactionDate = transactionDate;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(int bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getTransactionCategoryId() {
        return transactionCategoryId;
    }

    public void setTransactionCategoryId(int transactionCategoryId) {
        this.transactionCategoryId = transactionCategoryId;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "TransactionDTO{" +
                "id=" + id +
                ", bankAccountId=" + bankAccountId +
                ", amount=" + amount +
                ", transactionCategoryId=" + transactionCategoryId +
                ", transactionDate='" + transactionDate + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }

    public Transaction toTransactionEntity() {
        Transaction transaction = new Transaction();
        transaction.setId(this.id);
        transaction.setBankAccount(new BankAccountServiceImpl().getBankAccountById(this.bankAccountId));
        transaction.setAmount(this.amount);
        transaction.setTransactionCategory(new TransactionCategoryServiceImpl()
                .getTransactionCategoryById(this.transactionCategoryId));
        transaction.setTransactionDate(this.transactionDate);
        transaction.setComment(this.comment);

        return transaction;
    }
}
