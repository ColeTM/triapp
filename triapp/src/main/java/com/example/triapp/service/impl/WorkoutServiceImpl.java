package com.example.triapp.service.impl;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.triapp.data.DataProvider;
import com.example.triapp.dto.workout.CreateWorkoutRequest;
import com.example.triapp.model.Bike;
import com.example.triapp.model.Run;
import com.example.triapp.model.Swim;
import com.example.triapp.model.Workout;
import com.example.triapp.service.WorkoutService;

@Service
public class WorkoutServiceImpl implements WorkoutService {

    private final DataProvider dataProvider;

    public WorkoutServiceImpl(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    /**
     * Creates a workout and, if subtype data is present on the request,
     * also creates the associated Run/Bike/Swim record in the same transaction.
     */
    @Transactional
    public Workout saveWithSubtype(Workout workout, CreateWorkoutRequest req) {
        try {
            long workoutId = dataProvider.createWorkout(workout);

            // Create the subtype record if its data was provided
            String type = workout.getWorkoutType();
            if ("RUN".equalsIgnoreCase(type) && req.getRunData() != null) {
                Run run = new Run();
                run.setWorkoutId(workoutId);
                run.setDistanceMiles(req.getRunData().getDistanceMiles());
                run.setAvgPaceSecPerMile(req.getRunData().getAvgPaceSecPerMile());
                run.setElevationGainFeet(req.getRunData().getElevationGainFeet());
                run.setSteps(req.getRunData().getSteps());
                run.setAvgCadenceSpm(req.getRunData().getAvgCadenceSpm());
                dataProvider.createRun(run);
            } else if ("BIKE".equalsIgnoreCase(type) && req.getBikeData() != null) {
                Bike bike = new Bike();
                bike.setWorkoutId(workoutId);
                bike.setDistanceMiles(req.getBikeData().getDistanceMiles());
                bike.setAvgSpeedMph(req.getBikeData().getAvgSpeedMph());
                bike.setMaxSpeedMph(req.getBikeData().getMaxSpeedMph());
                bike.setElevationGainFeet(req.getBikeData().getElevationGainFeet());
                bike.setAvgPedalRateRpm(req.getBikeData().getAvgPedalRateRpm());
                dataProvider.createBike(bike);
            } else if ("SWIM".equalsIgnoreCase(type) && req.getSwimData() != null) {
                Swim swim = new Swim();
                swim.setWorkoutId(workoutId);
                swim.setDistanceYards(req.getSwimData().getDistanceYards());
                swim.setAvgPaceSecPer100Y(req.getSwimData().getAvgPaceSecPer100Y());
                swim.setAvgStrokeRateSpm(req.getSwimData().getAvgStrokeRateSpm());
                dataProvider.createSwim(swim);
            }

            return dataProvider.readWorkoutById(workoutId);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create workout with subtype", e);
        }
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
            List<Workout> base = dataProvider.readAllWorkouts();

            return base.stream()
                    .filter(w -> {
                        if (id != null) {
                            Long wid = w.getId();
                            if (wid == null || !id.equals(wid)) return false;
                        }
                        if (userId != null) {
                            Long uid = w.getUserId();
                            if (uid == null || !userId.equals(uid)) return false;
                        }
                        if (workoutType != null) {
                            String wt = w.getWorkoutType();
                            if (wt == null || !wt.equalsIgnoreCase(workoutType)) return false;
                        }
                        if (dateFrom != null) {
                            LocalDateTime d = w.getDate();
                            if (d == null || d.isBefore(dateFrom)) return false;
                        }
                        if (dateTo != null) {
                            LocalDateTime d = w.getDate();
                            if (d == null || d.isAfter(dateTo)) return false;
                        }
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
