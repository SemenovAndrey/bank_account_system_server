package ru.mai.information_system.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "bank_account_types")
public class BankAccountType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "type")
    private String type;

    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH },
            mappedBy = "bankAccountType",
            fetch = FetchType.EAGER)
    private List<BankAccount> bankAccountList;

    public BankAccountType() {}

    public BankAccountType(String type) {
        this.type = type;
    }

    public BankAccountType(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<BankAccount> getBankAccountList() {
        return bankAccountList;
    }

    public void setBankAccountList(List<BankAccount> bankAccountList) {
        this.bankAccountList = bankAccountList;
    }

    @Override
    public String toString() {
        return "BankAccountType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", bankAccountList=" + bankAccountList +
                '}';
    }
}
