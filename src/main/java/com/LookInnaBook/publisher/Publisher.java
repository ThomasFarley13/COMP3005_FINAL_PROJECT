package com.LookInnaBook.publisher;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Publisher {

    @Id
    @Getter
    @Setter
    private String publisher_name;
    @Getter
    @Setter
    private String address;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private long bank_account;

    public Publisher() {
    }

    //default constructor
    public Publisher(String publisher_name, String address, String email, long bank_account) {
        this.publisher_name = publisher_name;
        this.address = address;
        this.email = email;
        this.bank_account = bank_account;
    }

    @Override
    public String toString() {
        return "Publisher{" +
                "publisher=" + publisher_name +
                ", email='" + email + '\'' +
                '}';
    }

}
