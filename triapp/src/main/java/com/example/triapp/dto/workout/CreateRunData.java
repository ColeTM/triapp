package com.example.triapp.dto.workout;

/**
 * Nested run-specific fields inside CreateWorkoutRequest.
 * All fields are optional — only workoutId is set by the service automatically.
 */
public class CreateRunData {
    private Double distanceMiles;
    private Integer avgPaceSecPerMile;
    private Integer elevationGainFeet;
    private Integer steps;
    private Integer avgCadenceSpm;

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
