package com.example.triapp.model;

import java.time.LocalDateTime;

public class Workout {
    private Long id;
    private Long userId;
    private String workoutType; // "RUN", "BIKE", "SWIM"
    private LocalDateTime date;
    private Integer movingTimeSec;    // moving time in seconds
    private Integer elapsedTimeSec;   // elapsed time in seconds (includes pauses)
    private Integer avgHeartRate;     // bpm
    private Integer calories;         // kcal

    public Workout() {}
    public Workout(Long id, Long userId, String workoutType, LocalDateTime date,
                   Integer movingTimeSec, Integer elapsedTimeSec, Integer avgHeartRate, Integer calories) {
        this.id = id; this.userId = userId; this.workoutType = workoutType; this.date = date;
        this.movingTimeSec = movingTimeSec; this.elapsedTimeSec = elapsedTimeSec;
        this.avgHeartRate = avgHeartRate; this.calories = calories;
    }

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
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

    @Override
    public String toString() {
        return "Workout{" + "id=" + id + ", userId=" + userId + ", workoutType='" + workoutType + '\'' +
                ", date=" + date + ", movingTimeSec=" + movingTimeSec +
                ", elapsedTimeSec=" + elapsedTimeSec + ", avgHeartRate=" + avgHeartRate +
                ", calories=" + calories + '}';
    }
}
