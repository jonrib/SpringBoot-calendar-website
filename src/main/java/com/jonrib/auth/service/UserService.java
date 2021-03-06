package com.jonrib.auth.service;

import java.util.List;

import com.jonrib.auth.model.User;

public interface UserService {
    Object save(User user);

    User findByUsername(String username);
    
    List<User> findAll ();
}
