package ru.mai.information_system.dto;

import ru.mai.information_system.entity.TransactionByDate;
import ru.mai.information_system.service.BankAccountServiceImpl;
import ru.mai.information_system.service.TransactionCategoryServiceImpl;

public class TransactionByDateDTO {

    private int id;
    private int bankAccountId;
    private double amount;
    private int transactionCategoryId;
    private String transactionDate;
    private String comment;

    public TransactionByDateDTO() {}

    public TransactionByDateDTO(int id, int bankAccountId, double amount,
                                int transactionCategoryId, String transactionDate) {
        this.id = id;
        this.bankAccountId = bankAccountId;
        this.amount = amount;
        this.transactionCategoryId = transactionCategoryId;
        this.transactionDate = transactionDate;
    }

    public TransactionByDateDTO(int id, int bankAccountId, double amount,
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
        return "TransactionByDateDTO{" +
                "id=" + id +
                ", bankAccountId=" + bankAccountId +
                ", amount=" + amount +
                ", transactionCategoryId=" + transactionCategoryId +
                ", transactionDate='" + transactionDate + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }

    public TransactionByDate toTransactionByDateEntity() {
        TransactionByDate transactionByDate = new TransactionByDate();
        transactionByDate.setId(this.id);
        transactionByDate.setBankAccount(new BankAccountServiceImpl().getBankAccountById(this.bankAccountId));
        transactionByDate.setAmount(this.amount);
        transactionByDate.setTransactionCategory(new TransactionCategoryServiceImpl()
                .getTransactionCategoryById(this.transactionCategoryId));
        transactionByDate.setTransactionDate(this.transactionDate);
        transactionByDate.setComment(this.comment);

        return transactionByDate;
    }
}
