package com.LookInnaBook.billinginfo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface BillingInfoRepository extends CrudRepository<BillingInfo, Long> {

    //basic search to find billing info by a username
    BillingInfo findByUsername(String username);

    //inserts a new record into billing info for a user
    @Modifying
    @Query(value = "INSERT INTO billing_info" +
            " VALUES ( :username , :credit , :ccv , :expiration , :firstname , :lastname , :postal ) ;", nativeQuery = true)
    @Transactional
    void insertRecord(@Param("username") String username,
                      @Param("credit") long credit,
                      @Param("ccv") int ccv,
                      @Param("expiration") String expiration,
                      @Param("firstname") String firstname,
                      @Param("lastname") String lastname,
                      @Param("postal") String postal1);


    //updates the billing info for a user
    @Modifying
    @Query(value = "update Billing_Info " +
            "set credit_card_number = :credit , ccv = :ccv , expiration_date = :expiration , first_name = :firstname , last_name = :lastname , postal_code = :postal" +
            " where username = :username ;", nativeQuery = true)
    @Transactional
    void updateRecord(@Param("username") String username,
                      @Param("credit") long credit,
                      @Param("ccv") int ccv,
                      @Param("expiration") String expiration,
                      @Param("firstname") String firstname,
                      @Param("lastname") String lastname,
                      @Param("postal") String postal1);
}
