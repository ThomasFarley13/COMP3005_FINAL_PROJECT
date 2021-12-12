package com.LookInnaBook.basket;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BasketRepository extends CrudRepository<Basket, Long> {

    //basic select clause for variable "SELECT * FROM Basket WHERE basket_id = :basket_id"
    Basket findByBasketId(int basket_id);

    //find all baskets with an attached username "SELECT * FROM Basket WHERE username = :username"
    List<Basket> findByUsername(String username);

}
