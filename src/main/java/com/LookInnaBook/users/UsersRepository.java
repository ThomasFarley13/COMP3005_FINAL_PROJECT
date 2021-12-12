package com.LookInnaBook.users;

import org.springframework.data.repository.CrudRepository;


public interface UsersRepository extends CrudRepository<Users, Long> {

    //finds users by their username and password "SELECT * FROM users WHERE username = :username AND passwd = :passwd"
    Users findByUsernameAndPasswd(String username, String passwd);
}
