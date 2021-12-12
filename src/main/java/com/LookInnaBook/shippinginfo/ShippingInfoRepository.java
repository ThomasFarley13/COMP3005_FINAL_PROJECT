package com.LookInnaBook.shippinginfo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ShippingInfoRepository extends CrudRepository<ShippingInfo, Long> {

    //find shipping info for a user "SELECT * FROM shipping_info WHERE username = :username"
    ShippingInfo findByUsername(String username);

    //query to insert new shipping info for a user
    @Modifying
    @Query(value = "INSERT INTO shipping_info" +
            " VALUES ( :username, :country, :state_prov, :city, :address, :postal ) ;", nativeQuery = true)
    @Transactional
    void insertRecord(@Param("username") String username,
                      @Param("country") String country,
                      @Param("state_prov") String state_prov,
                      @Param("city") String city,
                      @Param("address") String address,
                      @Param("postal") String postal2);


    //updates existing shipping info for a user
    @Modifying
    @Query(value = "update Shipping_Info " +
            "set country = :country , state_province = :state_prov , city = :city , address = :address , postal_code = :postal" +
            " where username = :username ;", nativeQuery = true)
    @Transactional
    void updateRecord(@Param("username") String username,
                      @Param("country") String country,
                      @Param("state_prov") String state_prov,
                      @Param("city") String city,
                      @Param("address") String address,
                      @Param("postal") String postal2);
}
