package ru.mai.information_system.entity;

import ru.mai.information_system.dto.TransactionCategoryDTO;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "transaction_categories")
public class TransactionCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH },
            fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "type")
    private boolean type; // true - доход, false - трата
    @Column(name = "category")
    private String category;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "transactionCategory",
            fetch = FetchType.LAZY)
    private List<Transaction> transactionList;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "transactionCategory",
            fetch = FetchType.LAZY)
    private List<TransactionByDate> transactionByDateList;

    public TransactionCategory() {}

    public TransactionCategory(boolean type, String category) {
        this.type = type;
        this.category = category;
    }

    public TransactionCategory(User user, boolean type, String category) {
        this.user = user;
        this.type = type;
        this.category = category;
    }

    public TransactionCategory(int id, User user, boolean type, String category) {
        this.id = id;
        this.user = user;
        this.type = type;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
        return "TransactionCategory{" +
                "id=" + id +
                ", user=" + user +
                ", type=" + type +
                ", category='" + category + '\'' +
                '}';
    }

    public TransactionCategoryDTO transactionCategoryDTO() {
        TransactionCategoryDTO transactionCategoryDTO = new TransactionCategoryDTO();
        transactionCategoryDTO.setId(this.id);
        transactionCategoryDTO.setUserId(this.user.getId());
        transactionCategoryDTO.setType(this.type);
        transactionCategoryDTO.setCategory(this.category);

        return transactionCategoryDTO;
    }
}
