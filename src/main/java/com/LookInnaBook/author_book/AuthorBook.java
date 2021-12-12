package com.LookInnaBook.author_book;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AuthorBook {

    @Id
    @Getter
    @Setter
    private int id;
    @Getter
    @Setter
    private long isbn;


    public AuthorBook() {
    }

    //default constructor
    public AuthorBook(int id, long ISBN) {
        this.id = id;
        this.isbn = ISBN;

    }

    @Override
    public String toString() {
        return "IBSN: " + isbn + " ID: " + id;
    }

}
