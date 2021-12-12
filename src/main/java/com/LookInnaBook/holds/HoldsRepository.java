package com.LookInnaBook.holds;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface HoldsRepository extends CrudRepository<Holds, Long> {

    //finds all holds relationships for a basket "SELECT * FROM holds WHERE basket_id = :basket_id"
    List<Holds> findByBasketId(int basket_id);

    //finds a holds relationship for a given id and book "SELECT * FROM holds WHERE basket_id = :basket_id AND isbn = :book"
    Holds findByBasketIdAndIsbn(int basketId, long book);

    //updates the number of books that are being held in the basket
    @Modifying
    @Transactional
    @Query("update Holds h set h.quantity = h.quantity + 1 where h.basketId = :basketId and h.isbn = :isbn ")
    void updateQuantity(@Param("basketId") int basketId, @Param("isbn") long isbn);

    //adds a new holds relation
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Holds VALUES ( :basket , :isbn , :quantity ) ;", nativeQuery = true)
    void addNew(@Param("basket") int basket,@Param("isbn") long isbn, @Param("quantity") int quantity );

    //gets all books sold and sorts then by most copies sold
    @Query(value = "select  isbn, sum(quantity) \n" +
            "from holds as h\n" +
            "group by h.isbn\n" +
            "order by sum(h.quantity) DESC;", nativeQuery = true)
    List<String> getMostCopies();

    //gets all sales as well as the purchase cost, publisher cut, stock numbers, and stock cost
    @Query(value = "select  sum(h.quantity), b.purchase_cost, b.publisher_cut, b.stock, b.stock_cost\n" +
            "from holds as h, book as b\n" +
            "where h.isbn = b.isbn\n" +
            "group by b.stock, b.stock_cost, b.purchase_cost, b.publisher_cut;", nativeQuery = true)
    List<String> getSalesExpenditures();

    //gets all books sold and sorts them by genre
    @Query(value = "select  b.genre, sum(h.quantity), b.purchase_cost\n" +
            "from holds as h, book as b\n" +
            "where h.isbn = b.isbn\n" +
            "group by b.genre, b.purchase_cost, b.isbn\n" +
            "order by genre;", nativeQuery = true)
    List<String> getSalesGenre();

    //gets all books sold and sorts them by author
    @Query( value = "select  author.first_name, author.last_name, sum(h.quantity), b.purchase_cost\n" +
            "from holds as h, author_book as ab, author, book as b  \n" +
            "where h.isbn = ab.isbn AND ab.id = author.id AND b.isbn = ab.isbn\n" +
            "group by author.id, b.purchase_cost, b.isbn\n" +
            "order by author.id;", nativeQuery = true)
    List<String> getSalesAuthor();
}
