package com.LookInnaBook.users;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Users {

    @Id
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String passwd;
    @Getter
    @Setter
    private String first_name;
    @Getter
    @Setter
    private String last_name;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private long phone_number;
    @Getter
    @Setter
    private String user_role;

    public Users() {
    }

    //default constructor
    public Users(String username, String passwd, String first_name, String last_name, String email, long phone_number, String user_role) {
        this.username = username;
        this.passwd = passwd;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.phone_number = phone_number;
        this.user_role = user_role;
    }

    //constructor only for displaying a username
    public Users(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Users{" +
                "Users=" + username +
                ", email='" + email + '\'' +
                '}';
    }

}
