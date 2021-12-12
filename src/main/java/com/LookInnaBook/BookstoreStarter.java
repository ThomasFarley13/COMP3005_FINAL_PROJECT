package com.LookInnaBook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com")
public class BookstoreStarter implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(BookstoreStarter.class);


    public static void main(String[] args) {

        SpringApplication.run(BookstoreStarter.class, args);
    }

    @Override
    public void run(String... args) {


        log.info("Server now up..");

    }

}