package com.LookInnaBook.author;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    @Getter
    @Setter
    private int id;
    @Column(name = "first_name", nullable = false)
    @Getter
    @Setter
    private String firstname;
    @Column(name = "last_name", nullable = false)
    @Getter
    @Setter
    private String lastname;


    public Author() {
    }

    //default constructor
    public Author(String first_name, String last_name) {
        this.firstname = first_name;
        this.lastname = last_name;

    }

    @Override
    public String toString() {
        return "Author: " + firstname + " " + lastname;
    }

}
