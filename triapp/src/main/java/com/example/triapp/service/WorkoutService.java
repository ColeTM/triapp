package com.example.triapp.service;

import java.time.LocalDateTime;
import java.util.List;

import com.example.triapp.dto.workout.CreateWorkoutRequest;
import com.example.triapp.model.Workout;

public interface WorkoutService {
    Workout save(Workout workout);                                   // create (base only)
    Workout saveWithSubtype(Workout workout, CreateWorkoutRequest req); // create + subtype
    Workout update(Workout workout);                                 // update
    boolean delete(long id);                                         // delete
    Workout getById(long id);                                        // read by id
    List<Workout> getAll();                                          // read all
    List<Workout> getByUserId(long userId);
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
