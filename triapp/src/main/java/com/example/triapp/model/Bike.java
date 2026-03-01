package com.example.triapp.model;

public class Bike {
    private Long id;
    private Long workoutId;
    private Double distanceMiles;
    private Double avgSpeedMph;
    private Double maxSpeedMph;
    private Integer elevationGainFeet;
    private Integer avgPedalRateRpm; // revolutions per minute

    public Bike() {}
    public Bike(Long id, Long workoutId, Double distanceMiles, Double avgSpeedMph,
                Double maxSpeedMph, Integer elevationGainFeet, Integer avgPedalRateRpm) {
        this.id = id; this.workoutId = workoutId; this.distanceMiles = distanceMiles;
        this.avgSpeedMph = avgSpeedMph; this.maxSpeedMph = maxSpeedMph;
        this.elevationGainFeet = elevationGainFeet; this.avgPedalRateRpm = avgPedalRateRpm;
    }

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
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

    @Override
    public String toString() {
        return "Bike{" + "id=" + id + ", workoutId=" + workoutId + ", distanceMiles=" + distanceMiles +
                ", avgSpeedMph=" + avgSpeedMph + ", maxSpeedMph=" + maxSpeedMph +
                ", elevationGainFeet=" + elevationGainFeet + ", avgPedalRateRpm=" + avgPedalRateRpm + '}';
    }
}
