package com.LookInnaBook.book;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.LookInnaBook.author.Author;
import com.LookInnaBook.author_book.AuthorBook;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {

    //query to find all books by title
    List<Book> findByTitle(String title);

    //query to find all distinct genres
    @Query("SELECT DISTINCT genre FROM Book")
    List<String> findAllGenres();

    //query to find books by title genre and isbn
    List<Book> findByTitleAndGenreAndIsbn(String title, String genre, long isbn);

    //query to find books by title and genre
    List<Book> findByTitleAndGenre(String title, String genre);

    //query to find books by title and isbn
    List<Book> findByTitleAndIsbn(String title, long isbn);

    //query to find books by genre and isbn
    List<Book> findByGenreAndIsbn(String genre, long isbn);

    //query to find books by genre
    List<Book> findByGenre(String genre);

    //query to find books by their isbn
    List<Book> findByIsbn(long isbn);

    //returns the author attached to a book
    @Query(value = "SELECT first_name, last_name " +
            "FROM Book, Author_Book, Author " +
            "WHERE :ISBN = author_book.isbn AND author.id = author_book.id;", nativeQuery = true)
    String[] getAuthor(@Param("ISBN") long isbn);

    //returns a list of books attached to an author name
    @Query(value = "SELECT Book.isbn, title, genre, page_length, purchase_cost, publisher_name, publisher_cut, stock, stock_cost, order_more " +
            "FROM book, Author_Book, Author " +
            "WHERE Author.First_name = :authorFirst AND Author.Last_name = :authorLast AND Author_Book.id = Author.id AND Book.isbn = Author_Book.isbn;", nativeQuery = true)
    List<Book> findByAuthor(@Param("authorFirst") String authorFirst, @Param("authorLast") String authorLast);

    //updates the stock amount for a book
    @Modifying
    @Transactional
    @Query("update Book b set b.stock = b.stock + :stock where b.isbn = :isbn ")
    void updateStock(@Param("isbn") long isbn, @Param("stock") int stock);
}
