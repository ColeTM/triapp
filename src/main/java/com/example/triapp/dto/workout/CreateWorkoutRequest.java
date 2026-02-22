package com.example.triapp.dto.workout;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class CreateWorkoutRequest {
    @NotNull
    private Long userId;
    @NotBlank
    private String workoutType; // RUN/BIKE/SWIM
    @NotNull
    private LocalDateTime date;
    private Integer movingTimeSec;
    private Integer elapsedTimeSec;
    private Integer avgHeartRate;
    private Integer calories;

    // getters & setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getWorkoutType() { return workoutType; }
    public void setWorkoutType(String workoutType) { this.workoutType = workoutType; }
    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }
    public Integer getMovingTimeSec() { return movingTimeSec; }
    public void setMovingTimeSec(Integer movingTimeSec) { this.movingTimeSec = movingTimeSec; }
    public Integer getElapsedTimeSec() { return elapsedTimeSec; }
    public void setElapsedTimeSec(Integer elapsedTimeSec) { this.elapsedTimeSec = elapsedTimeSec; }
    public Integer getAvgHeartRate() { return avgHeartRate; }
    public void setAvgHeartRate(Integer avgHeartRate) { this.avgHeartRate = avgHeartRate; }
    public Integer getCalories() { return calories; }
    public void setCalories(Integer calories) { this.calories = calories; }
}