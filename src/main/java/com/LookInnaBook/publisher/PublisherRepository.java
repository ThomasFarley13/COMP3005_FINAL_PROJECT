package com.LookInnaBook.publisher;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface PublisherRepository extends CrudRepository<Publisher, Long> {

    //finds all distinct publishers
    @Query("SELECT DISTINCT publisher_name FROM Publisher")
    List<String> findAllPublishers();

}
