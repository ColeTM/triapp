package com.example.triapp.dto.run;

import jakarta.validation.constraints.NotNull;

public class CreateRunRequest {
    @NotNull
    private Long workoutId;
    private Double distanceMiles;
    private Integer avgPaceSecPerMile;
    private Integer elevationGainFeet;
    private Integer steps;
    private Integer avgCadenceSpm;

    // getters / setters
    public Long getWorkoutId() { return workoutId; }
    public void setWorkoutId(Long workoutId) { this.workoutId = workoutId; }
    public Double getDistanceMiles() { return distanceMiles; }
    public void setDistanceMiles(Double distanceMiles) { this.distanceMiles = distanceMiles; }
    public Integer getAvgPaceSecPerMile() { return avgPaceSecPerMile; }
    public void setAvgPaceSecPerMile(Integer avgPaceSecPerMile) { this.avgPaceSecPerMile = avgPaceSecPerMile; }
    public Integer getElevationGainFeet() { return elevationGainFeet; }
    public void setElevationGainFeet(Integer elevationGainFeet) { this.elevationGainFeet = elevationGainFeet; }
    public Integer getSteps() { return steps; }
    public void setSteps(Integer steps) { this.steps = steps; }
    public Integer getAvgCadenceSpm() { return avgCadenceSpm; }
    public void setAvgCadenceSpm(Integer avgCadenceSpm) { this.avgCadenceSpm = avgCadenceSpm; }
}