package com.LookInnaBook.billinginfo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class BillingInfo {

    @Id
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private long credit_card_number;
    @Getter
    @Setter
    private int ccv;
    @Getter
    @Setter
    private String expiration_date;
    @Getter
    @Setter
    private String first_name;
    @Getter
    @Setter
    private String last_name;
    @Getter
    @Setter
    private String postal_code;


    public BillingInfo() {
    }

    //default constructor
    public BillingInfo(String username, long credit_card_number, int ccv, String expiration_date, String first_name, String last_name, String postal_code) {
        this.username = username;
        this.credit_card_number = credit_card_number;
        this.ccv = ccv;
        this.expiration_date = expiration_date;
        this.first_name = first_name;
        this.last_name = last_name;
        this.postal_code = postal_code;
    }


}
