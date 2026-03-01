package com.example.triapp.dto.bike;

public class UpdateBikeRequest {
    private Long workoutId;
    private Double distanceMiles;
    private Double avgSpeedMph;
    private Double maxSpeedMph;
    private Integer elevationGainFeet;
    private Integer avgPedalRateRpm;

    public Long getWorkoutId() { return workoutId; }
    public void setWorkoutId(Long workoutId) { this.workoutId = workoutId; }

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