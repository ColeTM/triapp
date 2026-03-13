package com.example.triapp.dto.workout;

/**
 * Nested bike-specific fields inside CreateWorkoutRequest.
 */
public class CreateBikeData {
    private Double distanceMiles;
    private Double avgSpeedMph;
    private Double maxSpeedMph;
    private Integer elevationGainFeet;
    private Integer avgPedalRateRpm;

    public Double getDistanceMiles() { return distanceMiles; }
    public void setDistanceMiles(Double distanceMiles) { this.distanceMiles = distanceMiles; }
    public Double getAvgSpeedMph() { return avgSpeedMph; }
    public void setAvgSpeedMph(Double avgSpeedMph) { this.avgSpeedMph = avgSpeedMph; }
    public Double getMaxSpeedMph() { return maxSpeedMph; }
    public void setMaxSpeedMph(Double maxSpeedMph) { this.maxSpeedMph = maxSpeedMph; }
    public Integer getElevationGainFeet() { return elevationGainFeet; }
    public void setElevationGainFeet(Integer elevationGainFeet) { this.elevationGainFeet = elevationGainFeet; }
    public Integer getAvgPedalRateRpm() { return avgPedalRateRpm; }
    public void setAvgPedalRateRpm(Integer avgPedalRateRpm) { this.avgPedalRateRpm = avgPedalRateRpm; }
}
