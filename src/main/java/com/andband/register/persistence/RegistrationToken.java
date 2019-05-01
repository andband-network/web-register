package com.andband.register.persistence;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "registration_token")
public class RegistrationToken {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;

    @Column(name = "token_string", nullable = false, updatable = false)
    private String tokenString;

    @Column(name = "account_id", nullable = false, updatable = false)
    private String accountId;

    @Column(name = "expiry_date", nullable = false, updatable = false)
    private Date expiryDate;

    @PrePersist
    protected void onCreate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        expiryDate = cal.getTime();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTokenString() {
        return tokenString;
    }

    public void setTokenString(String tokenString) {
        this.tokenString = tokenString;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

}
