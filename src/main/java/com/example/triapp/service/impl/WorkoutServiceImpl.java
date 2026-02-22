package com.example.triapp.service.impl;

import com.example.triapp.data.DataProvider;
import com.example.triapp.model.Workout;
import com.example.triapp.service.WorkoutService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class WorkoutServiceImpl implements WorkoutService {

    private final DataProvider dataProvider;

    public WorkoutServiceImpl(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @Override
    @Transactional
    public Workout save(Workout workout) {
        try {
            long id = dataProvider.createWorkout(workout);
            return dataProvider.readWorkoutById(id);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create workout", e);
        }
    }

    @Override
    @Transactional
    public Workout update(Workout workout) {
        try {
            boolean ok = dataProvider.updateWorkout(workout);
            if (!ok) throw new RuntimeException("Workout update returned false");
            return dataProvider.readWorkoutById(workout.getId());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update workout", e);
        }
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        try {
            return dataProvider.deleteWorkout(id);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete workout", e);
        }
    }

    @Override
    public Workout getById(long id) {
        try {
            return dataProvider.readWorkoutById(id);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to read workout by id", e);
        }
    }

    @Override
    public List<Workout> getAll() {
        try {
            return dataProvider.readAllWorkouts();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to read all workouts", e);
        }
    }

    @Override
    public List<Workout> getByUserId(long userId) {
        try {
            return dataProvider.readWorkoutsByUserId(userId);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to read workouts by user id", e);
        }
    }
}