package com.LookInnaBook.holds;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Holds {

    @Id
    @Column(name = "basket_id", nullable = false)
    @Getter
    @Setter
    private int basketId;
    @Getter
    @Setter
    private long isbn;
    @Getter
    @Setter
    private int quantity;


    public Holds() {
    }

    //default constructor
    public Holds(int basket_id, long ISBN, int quantity) {
        this.basketId = basket_id;
        this.isbn = ISBN;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Basket ID: " + basketId;
    }

}
