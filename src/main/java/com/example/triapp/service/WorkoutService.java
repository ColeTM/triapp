package com.example.triapp.service;

import com.example.triapp.model.Workout;

import java.util.List;

public interface WorkoutService {
    Workout save(Workout workout);      // create
    Workout update(Workout workout);    // update
    boolean delete(long id);            // delete
    Workout getById(long id);           // read by id
    List<Workout> getAll();             // read all
    List<Workout> getByUserId(long userId); // additional convenience
}