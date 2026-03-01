package com.example.triapp.mapper;

import com.example.triapp.dto.run.CreateRunRequest;
import com.example.triapp.dto.run.RunDto;
import com.example.triapp.dto.run.UpdateRunRequest;
import com.example.triapp.dto.user.*;
import com.example.triapp.dto.workout.*;
import com.example.triapp.model.User;
import com.example.triapp.model.Workout;

public class ApiMapper {

    // --- User ---
    public static User toUser(CreateUserRequest r) {
        User u = new User();
        u.setFirstName(r.getFirstName());
        u.setLastName(r.getLastName());
        u.setEmail(r.getEmail());
        u.setPassword(r.getPassword());
        u.setHeightInches(r.getHeightInches());
        u.setWeightPounds(r.getWeightPounds());
        return u;
    }

    public static void updateUserFrom(UpdateUserRequest r, User u) {
        if (r.getFirstName() != null) u.setFirstName(r.getFirstName());
        if (r.getLastName() != null) u.setLastName(r.getLastName());
        if (r.getEmail() != null) u.setEmail(r.getEmail());
        if (r.getPassword() != null) u.setPassword(r.getPassword());
        if (r.getHeightInches() != null) u.setHeightInches(r.getHeightInches());
        if (r.getWeightPounds() != null) u.setWeightPounds(r.getWeightPounds());
    }

    public static UserDto toUserDto(User u) {
        if (u == null) return null;
        UserDto d = new UserDto();
        d.setId(u.getId());
        d.setFirstName(u.getFirstName());
        d.setLastName(u.getLastName());
        d.setEmail(u.getEmail());
        d.setHeightInches(u.getHeightInches());
        d.setWeightPounds(u.getWeightPounds());
        return d;
    }

    // --- Workout ---
    public static Workout toWorkout(CreateWorkoutRequest r) {
        Workout w = new Workout();
        w.setUserId(r.getUserId());
        w.setWorkoutType(r.getWorkoutType());
        w.setDate(r.getDate());
        w.setMovingTimeSec(r.getMovingTimeSec());
        w.setElapsedTimeSec(r.getElapsedTimeSec());
        w.setAvgHeartRate(r.getAvgHeartRate());
        w.setCalories(r.getCalories());
        return w;
    }

    public static void updateWorkoutFrom(UpdateWorkoutRequest r, Workout w) {
        if (r.getUserId() != null) w.setUserId(r.getUserId());
        if (r.getWorkoutType() != null) w.setWorkoutType(r.getWorkoutType());
        if (r.getDate() != null) w.setDate(r.getDate());
        if (r.getMovingTimeSec() != null) w.setMovingTimeSec(r.getMovingTimeSec());
        if (r.getElapsedTimeSec() != null) w.setElapsedTimeSec(r.getElapsedTimeSec());
        if (r.getAvgHeartRate() != null) w.setAvgHeartRate(r.getAvgHeartRate());
        if (r.getCalories() != null) w.setCalories(r.getCalories());
    }

    public static WorkoutDto toWorkoutDto(Workout w) {
        if (w == null) return null;
        WorkoutDto d = new WorkoutDto();
        d.setId(w.getId());
        d.setUserId(w.getUserId());
        d.setWorkoutType(w.getWorkoutType());
        d.setDate(w.getDate());
        d.setMovingTimeSec(w.getMovingTimeSec());
        d.setElapsedTimeSec(w.getElapsedTimeSec());
        d.setAvgHeartRate(w.getAvgHeartRate());
        d.setCalories(w.getCalories());
        return d;
    }

    // Run mapping
    public static com.example.triapp.model.Run toRun(CreateRunRequest r) {
        com.example.triapp.model.Run run = new com.example.triapp.model.Run();
        run.setWorkoutId(r.getWorkoutId());
        run.setDistanceMiles(r.getDistanceMiles());
        run.setAvgPaceSecPerMile(r.getAvgPaceSecPerMile());
        run.setElevationGainFeet(r.getElevationGainFeet());
        run.setSteps(r.getSteps());
        run.setAvgCadenceSpm(r.getAvgCadenceSpm());
        return run;
    }

    public static void updateRunFrom(UpdateRunRequest r, com.example.triapp.model.Run run) {
        if (r.getWorkoutId() != null) run.setWorkoutId(r.getWorkoutId());
        if (r.getDistanceMiles() != null) run.setDistanceMiles(r.getDistanceMiles());
        if (r.getAvgPaceSecPerMile() != null) run.setAvgPaceSecPerMile(r.getAvgPaceSecPerMile());
        if (r.getElevationGainFeet() != null) run.setElevationGainFeet(r.getElevationGainFeet());
        if (r.getSteps() != null) run.setSteps(r.getSteps());
        if (r.getAvgCadenceSpm() != null) run.setAvgCadenceSpm(r.getAvgCadenceSpm());
    }

    public static RunDto toRunDto(com.example.triapp.model.Run run) {
        if (run == null) return null;
        RunDto d = new RunDto();
        d.setId(run.getId());
        d.setWorkoutId(run.getWorkoutId());
        d.setDistanceMiles(run.getDistanceMiles());
        d.setAvgPaceSecPerMile(run.getAvgPaceSecPerMile());
        d.setElevationGainFeet(run.getElevationGainFeet());
        d.setSteps(run.getSteps());
        d.setAvgCadenceSpm(run.getAvgCadenceSpm());
        return d;
    }

    // --- Bike mapping ---
    public static com.example.triapp.model.Bike toBike(com.example.triapp.dto.bike.CreateBikeRequest r) {
        com.example.triapp.model.Bike b = new com.example.triapp.model.Bike();
        b.setWorkoutId(r.getWorkoutId());
        b.setDistanceMiles(r.getDistanceMiles());
        b.setAvgSpeedMph(r.getAvgSpeedMph());
        b.setMaxSpeedMph(r.getMaxSpeedMph());
        b.setElevationGainFeet(r.getElevationGainFeet());
        b.setAvgPedalRateRpm(r.getAvgPedalRateRpm());
        return b;
    }

    public static void updateBikeFrom(com.example.triapp.dto.bike.UpdateBikeRequest r, com.example.triapp.model.Bike b) {
        if (r.getWorkoutId() != null) b.setWorkoutId(r.getWorkoutId());
        if (r.getDistanceMiles() != null) b.setDistanceMiles(r.getDistanceMiles());
        if (r.getAvgSpeedMph() != null) b.setAvgSpeedMph(r.getAvgSpeedMph());
        if (r.getMaxSpeedMph() != null) b.setMaxSpeedMph(r.getMaxSpeedMph());
        if (r.getElevationGainFeet() != null) b.setElevationGainFeet(r.getElevationGainFeet());
        if (r.getAvgPedalRateRpm() != null) b.setAvgPedalRateRpm(r.getAvgPedalRateRpm());
    }

    public static com.example.triapp.dto.bike.BikeDto toBikeDto(com.example.triapp.model.Bike b) {
        if (b == null) return null;
        com.example.triapp.dto.bike.BikeDto d = new com.example.triapp.dto.bike.BikeDto();
        d.setId(b.getId());
        d.setWorkoutId(b.getWorkoutId());
        d.setDistanceMiles(b.getDistanceMiles());
        d.setAvgSpeedMph(b.getAvgSpeedMph());
        d.setMaxSpeedMph(b.getMaxSpeedMph());
        d.setElevationGainFeet(b.getElevationGainFeet());
        d.setAvgPedalRateRpm(b.getAvgPedalRateRpm());
        return d;
    }

    // --- Swim mapping ---
    public static com.example.triapp.model.Swim toSwim(com.example.triapp.dto.swim.CreateSwimRequest r) {
        com.example.triapp.model.Swim s = new com.example.triapp.model.Swim();
        s.setWorkoutId(r.getWorkoutId());
        s.setDistanceYards(r.getDistanceYards());
        s.setAvgPaceSecPer100Y(r.getAvgPaceSecPer100Y());
        s.setAvgStrokeRateSpm(r.getAvgStrokeRateSpm());
        return s;
    }

    public static void updateSwimFrom(com.example.triapp.dto.swim.UpdateSwimRequest r, com.example.triapp.model.Swim s) {
        if (r.getWorkoutId() != null) s.setWorkoutId(r.getWorkoutId());
        if (r.getDistanceYards() != null) s.setDistanceYards(r.getDistanceYards());
        if (r.getAvgPaceSecPer100Y() != null) s.setAvgPaceSecPer100Y(r.getAvgPaceSecPer100Y());
        if (r.getAvgStrokeRateSpm() != null) s.setAvgStrokeRateSpm(r.getAvgStrokeRateSpm());
    }

    public static com.example.triapp.dto.swim.SwimDto toSwimDto(com.example.triapp.model.Swim s) {
        if (s == null) return null;
        com.example.triapp.dto.swim.SwimDto d = new com.example.triapp.dto.swim.SwimDto();
        d.setId(s.getId());
        d.setWorkoutId(s.getWorkoutId());
        d.setDistanceYards(s.getDistanceYards());
        d.setAvgPaceSecPer100Y(s.getAvgPaceSecPer100Y());
        d.setAvgStrokeRateSpm(s.getAvgStrokeRateSpm());
        return d;
    }
}