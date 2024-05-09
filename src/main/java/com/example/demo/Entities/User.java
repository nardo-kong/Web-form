package com.example.demo.Entities;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "users")
public class User {

    @Id
    private String accountId;

    private Long scaleId;

    public User() {
    }

    public User(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Long getScaleId() {
        return scaleId;
    }
}