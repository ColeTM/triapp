package com.example.triapp.service;

import java.time.LocalDateTime;
import java.util.List;

import com.example.triapp.model.Workout;

public interface WorkoutService {
    Workout save(Workout workout);      // create
    Workout update(Workout workout);    // update
    boolean delete(long id);            // delete
    Workout getById(long id);           // read by id
    List<Workout> getAll();             // read all
    List<Workout> getByUserId(long userId); // additional convenience
    /**
     * Find workouts by optional filters. Any parameter may be null.
     * - userId: exact match on user id
     * - workoutType: exact match on workout type
     * - dateFrom / dateTo: inclusive date/time range filter on the Workout.date field
     */
    List<Workout> findByFilters(
        Long id,
        Long userId,
        String workoutType,
        LocalDateTime dateFrom,
        LocalDateTime dateTo,
        Integer movingTimeSec,
        Integer minMovingTimeSec,
        Integer maxMovingTimeSec,
        Integer elapsedTimeSec,
        Integer minElapsedTimeSec,
        Integer maxElapsedTimeSec,
        Integer avgHeartRate,
        Integer minAvgHeartRate,
        Integer maxAvgHeartRate,
        Integer calories,
        Integer minCalories,
        Integer maxCalories
    );
}