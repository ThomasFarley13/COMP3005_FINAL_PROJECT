package com.LookInnaBook.orders;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_number", nullable = false, updatable = false)
    @Getter
    @Setter
    private int orderNumber;
    @Getter
    @Setter
    private int basket_id;
    @Getter
    @Setter
    private String shipping_status;


    public Orders() {
    }


    //default constructor
    public Orders(int basket_id, String shipping_status) {
        this.basket_id = basket_id;
        this.shipping_status = shipping_status;
    }

    @Override
    public String toString() {
        return "Order number: " + orderNumber;
    }

}
