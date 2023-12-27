package ru.mai.information_system.entity;

import ru.mai.information_system.dto.TransactionDTO;

import javax.persistence.*;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "bank_account_id")
    private BankAccount bankAccount;
    @Column(name = "amount")
    private double amount;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "transaction_category_id")
    private TransactionCategory transactionCategory;
    @Column(name = "transaction_date")
    private String transactionDate;

    public Transaction() {}

    public Transaction(BankAccount bankAccount, double amount, TransactionCategory transactionCategory,
                       String transactionDate) {
        this.bankAccount = bankAccount;
        this.amount = amount;
        this.transactionCategory = transactionCategory;
        this.transactionDate = transactionDate;
    }

    public Transaction(int id, BankAccount bankAccount, double amount, TransactionCategory transactionCategory,
                       String transactionDate) {
        this.id = id;
        this.bankAccount = bankAccount;
        this.amount = amount;
        this.transactionCategory = transactionCategory;
        this.transactionDate = transactionDate;
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

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", bankAccount=" + bankAccount +
                ", amount=" + amount +
                ", transactionCategory=" + transactionCategory +
                ", transactionDate='" + transactionDate + '\'' +
                '}';
    }

    public TransactionDTO toTransactionDTO() {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(this.id);
        transactionDTO.setBankAccountId(this.bankAccount.getId());
        transactionDTO.setAmount(this.amount);
        transactionDTO.setTransactionCategoryId(this.transactionCategory.getId());
        transactionDTO.setTransactionDate(this.transactionDate);

        return transactionDTO;
    }
}