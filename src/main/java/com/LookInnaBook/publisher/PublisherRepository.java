package com.LookInnaBook.publisher;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface PublisherRepository extends CrudRepository<Publisher, Long> {

    //finds all distinct publishers
    @Query("SELECT DISTINCT publisher_name FROM Publisher")
    List<String> findAllPublishers();

    //finds all emails with a given publisher name
    @Query(value = "SELECT email FROM Publisher WHERE publisher_name = :publisher_name", nativeQuery = true)
    String findPublisherEmail(@Param("publisher_name")String publisher_name);
}
