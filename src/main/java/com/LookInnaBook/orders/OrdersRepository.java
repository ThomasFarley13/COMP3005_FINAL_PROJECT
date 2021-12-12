package com.LookInnaBook.orders;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrdersRepository extends CrudRepository<Orders, Long> {

    //finds all orders by number "SELECT * FROM orders WHERE order_number = :order_number"
    Orders findByOrderNumber(int order_number);


}
