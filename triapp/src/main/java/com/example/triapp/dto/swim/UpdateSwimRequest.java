package com.example.triapp.dto.swim;

public class UpdateSwimRequest {
    private Long workoutId;
    private Integer distanceYards;
    private Integer avgPaceSecPer100Y;
    private Integer avgStrokeRateSpm;

    public Long getWorkoutId() { return workoutId; }
    public void setWorkoutId(Long workoutId) { this.workoutId = workoutId; }

    public Integer getDistanceYards() { return distanceYards; }
    public void setDistanceYards(Integer distanceYards) { this.distanceYards = distanceYards; }

    public Integer getAvgPaceSecPer100Y() { return avgPaceSecPer100Y; }
    public void setAvgPaceSecPer100Y(Integer avgPaceSecPer100Y) { this.avgPaceSecPer100Y = avgPaceSecPer100Y; }

    public Integer getAvgStrokeRateSpm() { return avgStrokeRateSpm; }
    public void setAvgStrokeRateSpm(Integer avgStrokeRateSpm) { this.avgStrokeRateSpm = avgStrokeRateSpm; }
}