package com.example.triapp.service.impl;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.triapp.data.DataProvider;
import com.example.triapp.model.Workout;
import com.example.triapp.service.WorkoutService;

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

    /**
     * Filter workouts in-memory using DataProvider results.
     * This avoids changing the DataProvider layer and is fine for moderate result sizes.
     */
    @Override
    public List<Workout> findByFilters(
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
    ) {
        try {
            // Start from full dataset (safe, consistent)
            List<Workout> base = dataProvider.readAllWorkouts();

            return base.stream()
                    .filter(w -> {
                        // id (exact)
                        if (id != null) {
                            Long wid = w.getId();
                            if (wid == null || !id.equals(wid)) return false;
                        }

                        // userId (exact)
                        if (userId != null) {
                            Long uid = w.getUserId();
                            if (uid == null || !userId.equals(uid)) return false;
                        }

                        // workoutType (exact, case-insensitive)
                        if (workoutType != null) {
                            String wt = w.getWorkoutType();
                            if (wt == null || !wt.equalsIgnoreCase(workoutType)) return false;
                        }

                        // date range
                        if (dateFrom != null) {
                            LocalDateTime d = w.getDate();
                            if (d == null || d.isBefore(dateFrom)) return false;
                        }
                        if (dateTo != null) {
                            LocalDateTime d = w.getDate();
                            if (d == null || d.isAfter(dateTo)) return false;
                        }

                        // movingTimeSec: exact or range
                        if (movingTimeSec != null) {
                            Integer v = w.getMovingTimeSec();
                            if (v == null || !movingTimeSec.equals(v)) return false;
                        }
                        if (minMovingTimeSec != null) {
                            Integer v = w.getMovingTimeSec();
                            if (v == null || v < minMovingTimeSec) return false;
                        }
                        if (maxMovingTimeSec != null) {
                            Integer v = w.getMovingTimeSec();
                            if (v == null || v > maxMovingTimeSec) return false;
                        }

                        // elapsedTimeSec: exact or range
                        if (elapsedTimeSec != null) {
                            Integer v = w.getElapsedTimeSec();
                            if (v == null || !elapsedTimeSec.equals(v)) return false;
                        }
                        if (minElapsedTimeSec != null) {
                            Integer v = w.getElapsedTimeSec();
                            if (v == null || v < minElapsedTimeSec) return false;
                        }
                        if (maxElapsedTimeSec != null) {
                            Integer v = w.getElapsedTimeSec();
                            if (v == null || v > maxElapsedTimeSec) return false;
                        }

                        // avgHeartRate: exact or range
                        if (avgHeartRate != null) {
                            Integer v = w.getAvgHeartRate();
                            if (v == null || !avgHeartRate.equals(v)) return false;
                        }
                        if (minAvgHeartRate != null) {
                            Integer v = w.getAvgHeartRate();
                            if (v == null || v < minAvgHeartRate) return false;
                        }
                        if (maxAvgHeartRate != null) {
                            Integer v = w.getAvgHeartRate();
                            if (v == null || v > maxAvgHeartRate) return false;
                        }

                        // calories: exact or range
                        if (calories != null) {
                            Integer v = w.getCalories();
                            if (v == null || !calories.equals(v)) return false;
                        }
                        if (minCalories != null) {
                            Integer v = w.getCalories();
                            if (v == null || v < minCalories) return false;
                        }
                        if (maxCalories != null) {
                            Integer v = w.getCalories();
                            if (v == null || v > maxCalories) return false;
                        }

                        return true;
                    })
                    .collect(Collectors.toList());

        } catch (SQLException e) {
            throw new RuntimeException("Failed to read filtered workouts", e);
        }
    }
}