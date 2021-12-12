package com.LookInnaBook.book;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.LookInnaBook.author.Author;
import com.LookInnaBook.author.AuthorRepository;
import com.LookInnaBook.author_book.AuthorBook;
import com.LookInnaBook.author_book.AuthorBookRepository;
import com.LookInnaBook.users.UsersRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;


@Entity
public class Book {

    @Transient
    @Getter
    @Setter
    private String author;


    @Id
    @Getter
    @Setter
    private long isbn;
    @Getter
    @Setter
    private String title;
    @Getter
    @Setter
    private String genre;
    @Getter
    @Setter
    private int page_length;
    @Getter
    @Setter
    private double purchase_cost;
    @Getter
    @Setter
    private String publisher_name;
    @Getter
    @Setter
    private double publisher_cut;
    @Getter
    @Setter
    private int stock;
    @Getter
    @Setter
    private double stock_cost;
    @Getter
    @Setter
    private boolean order_more;
    @Getter
    @Setter
    private double sale;


    public Book() {
    }

    //default constructor
    public Book(long ISBN, String title, String genre, int page_length, double purchase_cost, String publisher_name, double publisher_cut, int stock, double stock_cost, boolean order_more, double sale) {
        this.isbn = ISBN;
        this.title = title;
        this.genre = genre;
        this.page_length = page_length;
        this.purchase_cost = purchase_cost;
        this.publisher_name = publisher_name;
        this.publisher_cut = publisher_cut;
        this.stock = stock;
        this.stock_cost = stock_cost;
        this.order_more = order_more;
        this.sale = sale;
    }

    @Override
    public String toString() {
        return "Book{" +
                "ISBN=" + isbn +
                ", title='" + title + '\'' +
                '}';
    }


}
