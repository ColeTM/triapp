package com.example.triapp.dto.swim;

import java.io.Serializable;

public class SwimDto implements Serializable {
    private Long id;
    private Long workoutId;
    private Integer distanceYards;
    private Integer avgPaceSecPer100Y;
    private Integer avgStrokeRateSpm;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getWorkoutId() { return workoutId; }
    public void setWorkoutId(Long workoutId) { this.workoutId = workoutId; }

    public Integer getDistanceYards() { return distanceYards; }
    public void setDistanceYards(Integer distanceYards) { this.distanceYards = distanceYards; }

    public Integer getAvgPaceSecPer100Y() { return avgPaceSecPer100Y; }
    public void setAvgPaceSecPer100Y(Integer avgPaceSecPer100Y) { this.avgPaceSecPer100Y = avgPaceSecPer100Y; }

    public Integer getAvgStrokeRateSpm() { return avgStrokeRateSpm; }
    public void setAvgStrokeRateSpm(Integer avgStrokeRateSpm) { this.avgStrokeRateSpm = avgStrokeRateSpm; }
}