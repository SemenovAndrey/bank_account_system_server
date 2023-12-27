package ru.mai.information_system.entity;

import javax.persistence.*;

@Entity
@Table(name = "support")
public class Support {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "email")
    private String email;
    @Column(name = "message")
    private String message;
    @Column(name = "is_processed")
    private boolean isProcessed;

    public Support() {}

    public Support(String email, String message) {
        this.email = email;
        this.message = message;
        this.isProcessed = false;
    }

    public Support(int id, String email, String message, boolean isProcessed) {
        this.id = id;
        this.email = email;
        this.message = message;
        this.isProcessed = isProcessed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isProcessed() {
        return isProcessed;
    }

    public void setProcessed(boolean processed) {
        isProcessed = processed;
    }

    @Override
    public String toString() {
        return "Support{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", message='" + message + '\'' +
                ", isProcessed=" + isProcessed +
                '}';
    }
}
