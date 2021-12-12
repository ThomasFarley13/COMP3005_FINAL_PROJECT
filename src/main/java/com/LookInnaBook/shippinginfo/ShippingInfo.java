package com.LookInnaBook.shippinginfo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class ShippingInfo {

    @Id
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String country;
    @Getter
    @Setter
    private String state_province;
    @Getter
    @Setter
    private String city;
    @Getter
    @Setter
    private String address;
    @Getter
    @Setter
    private String postal_code;


    public ShippingInfo() {
    }

    //default constructor
    public ShippingInfo(String username, String country, String state_province, String city, String address, String postal_code) {
        this.username = username;
        this.country = country;
        this.state_province = state_province;
        this.city = city;
        this.address = address;
        this.postal_code = postal_code;
    }


}
