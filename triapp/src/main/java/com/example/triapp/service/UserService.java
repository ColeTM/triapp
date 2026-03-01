package com.example.triapp.service;

import com.example.triapp.model.User;

import java.util.List;

public interface UserService {
    User save(User user);           // create
    User update(User user);         // update
    boolean delete(long id);        // delete
    User getById(long id);          // read by id
    List<User> getAll();            // read all
}