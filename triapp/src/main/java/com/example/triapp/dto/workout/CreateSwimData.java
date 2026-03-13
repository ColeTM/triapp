package com.example.triapp.dto.workout;

/**
 * Nested swim-specific fields inside CreateWorkoutRequest.
 */
public class CreateSwimData {
    private Integer distanceYards;
    private Integer avgPaceSecPer100Y;
    private Integer avgStrokeRateSpm;

    public Integer getDistanceYards() { return distanceYards; }
    public void setDistanceYards(Integer distanceYards) { this.distanceYards = distanceYards; }
    public Integer getAvgPaceSecPer100Y() { return avgPaceSecPer100Y; }
    public void setAvgPaceSecPer100Y(Integer avgPaceSecPer100Y) { this.avgPaceSecPer100Y = avgPaceSecPer100Y; }
    public Integer getAvgStrokeRateSpm() { return avgStrokeRateSpm; }
    public void setAvgStrokeRateSpm(Integer avgStrokeRateSpm) { this.avgStrokeRateSpm = avgStrokeRateSpm; }
}
