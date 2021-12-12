package com.LookInnaBook.basket;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "basket_id", nullable = false, updatable = false)
    @Getter
    @Setter
    private int basketId;
    @Getter
    @Setter
    private String username;


    public Basket() {
    }

    //default constructor
    public Basket(String username) {
        this.username = username;

    }


}
