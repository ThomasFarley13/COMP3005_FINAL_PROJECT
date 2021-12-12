package com.LookInnaBook.author_book;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AuthorBookRepository extends CrudRepository<AuthorBook, Long> {

    //query to insert new author book relations into the author_book table
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO author_book VALUES ( :id , :isbn ) ;", nativeQuery = true)
    void insertNew(@Param("id") int id, @Param("isbn") long isbn);
}
