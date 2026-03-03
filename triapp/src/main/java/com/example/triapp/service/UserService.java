package com.example.triapp.service;

import com.example.triapp.model.User;

import java.util.List;

public interface UserService {
    User save(User user);           // create
    User update(User user);         // update
    boolean delete(long id);        // delete
    User getById(long id);          // read by id
    List<User> getAll();            // read all
    List<User> findByFilters(
        Long id,
        String firstName,
        String lastName,
        String email,
        Integer heightInches,
        Integer minHeightInches,
        Integer maxHeightInches,
        Double weightPounds,
        Double minWeightPounds,
        Double maxWeightPounds
    );
}