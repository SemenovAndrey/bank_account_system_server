package ru.mai.information_system.entity;

import ru.mai.information_system.dto.BankAccountDTO;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "bank_accounts")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH },
            fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH },
            fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_account_type_id")
    private BankAccountType bankAccountType;
    @Column(name = "creation_date")
    private String creationDate;
    @Column(name = "balance")
    private double balance;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "bankAccount",
            fetch = FetchType.LAZY)
    private List<Transaction> transactionList;

    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH },
            mappedBy = "bankAccount",
            fetch = FetchType.LAZY)
    private List<TransactionByDate> transactionByDateList;

    public BankAccount() {}

    public BankAccount(String name, String creationDate, double balance) {
        this.name = name;
        this.creationDate = creationDate;
        this.balance = balance;
    }

    public BankAccount(String name, User user, BankAccountType bankAccountType,
                       String creationDate, double balance) {
        this.name = name;
        this.user = user;
        this.bankAccountType = bankAccountType;
        this.creationDate = creationDate;
        this.balance = balance;
    }

    public BankAccount(int id, String name, User user, BankAccountType bankAccountType,
                       String creationDate, double balance) {
        this.id = id;
        this.name = name;
        this.user = user;
        this.bankAccountType = bankAccountType;
        this.creationDate = creationDate;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BankAccountType getBankAccountType() {
        return bankAccountType;
    }

    public void setBankAccountType(BankAccountType bankAccountType) {
        this.bankAccountType = bankAccountType;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    public List<TransactionByDate> getTransactionByDateList() {
        return transactionByDateList;
    }

    public void setTransactionByDateList(List<TransactionByDate> transactionByDateList) {
        this.transactionByDateList = transactionByDateList;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "id=" + id +
                ", userId=" + user.getId() +
                ", name='" + name + '\'' +
                ", bankAccountType=" + bankAccountType.getId() +
                ", creationDate=" + creationDate +
                ", balance=" + balance +
                '}';
    }

    public BankAccountDTO toBankAccountDTO() {
        BankAccountDTO bankAccountDTO = new BankAccountDTO();
        bankAccountDTO.setId(this.id);
        bankAccountDTO.setName(this.name);
        bankAccountDTO.setUserId(this.user.getId());
        bankAccountDTO.setBankAccountTypeId(this.bankAccountType.getId());
        bankAccountDTO.setCreationDate(this.creationDate);
        bankAccountDTO.setBalance(this.balance);

        return bankAccountDTO;
    }
}
