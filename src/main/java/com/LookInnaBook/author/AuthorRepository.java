package com.LookInnaBook.author;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthorRepository extends CrudRepository<Author, Long> {

    //equivalent to SQL query "SELECT * FROM author WHERE id = :id ;
    Author findById(String id);


    //query to get all author names
    @Query(value = "SELECT first_name, last_name FROM Author;", nativeQuery = true)
    List<String> findAllAuthors();

    //query to get all authors with a specific first and last name
    @Query(value = "SELECT * FROM Author WHERE first_name = :firstname  and last_name = :lastname ;", nativeQuery = true)
    List<Author> findByFirstnameLastname(@Param("firstname") String s, @Param("lastname") String s1);
}
