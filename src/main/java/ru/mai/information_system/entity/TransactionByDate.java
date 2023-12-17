package ru.mai.information_system.entity;

import ru.mai.information_system.dto.TransactionByDateDTO;

import javax.persistence.*;

@Entity
@Table(name = "transactions_by_date")
public class TransactionByDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH },
            fetch = FetchType.EAGER)
    @JoinColumn(name = "bank_account_id")
    private BankAccount bankAccount;
    @Column(name = "amount")
    private double amount;
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH },
            fetch = FetchType.EAGER)
    @JoinColumn(name = "transaction_category_id")
    private TransactionCategory transactionCategory;
    @Column(name = "transaction_date")
    private String transactionDate;
    @Column(name = "comment")
    private String comment;

    public TransactionByDate() {}

    public TransactionByDate(double amount, String transactionDate, String comment) {
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.comment = comment;
    }

    public TransactionByDate(BankAccount bankAccount, double amount, TransactionCategory transactionCategory,
                             String transactionDate, String comment) {
        this.bankAccount = bankAccount;
        this.amount = amount;
        this.transactionCategory = transactionCategory;
        this.transactionDate = transactionDate;
        this.comment = comment;
    }

    public TransactionByDate(int id, BankAccount bankAccount, double amount, TransactionCategory transactionCategory,
                             String transactionDate, String comment) {
        this.id = id;
        this.bankAccount = bankAccount;
        this.amount = amount;
        this.transactionCategory = transactionCategory;
        this.transactionDate = transactionDate;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TransactionCategory getTransactionCategory() {
        return transactionCategory;
    }

    public void setTransactionCategory(TransactionCategory transactionCategory) {
        this.transactionCategory = transactionCategory;
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
        return "TransactionByDate{" +
                "id=" + id +
                ", bankAccountId=" + bankAccount.getId() +
                ", amount=" + amount +
                ", transactionCategoryId=" + transactionCategory.getId() +
                ", transactionDate='" + transactionDate + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }

    public TransactionByDateDTO toTransactionByDateDTO() {
        TransactionByDateDTO transactionByDateDTO = new TransactionByDateDTO();
        transactionByDateDTO.setId(this.id);
        transactionByDateDTO.setBankAccountId(this.bankAccount.getId());
        transactionByDateDTO.setAmount(this.amount);
        transactionByDateDTO.setTransactionCategoryId(this.transactionCategory.getId());
        transactionByDateDTO.setTransactionDate(this.transactionDate);
        transactionByDateDTO.setComment(this.comment);

        return transactionByDateDTO;
    }
}
