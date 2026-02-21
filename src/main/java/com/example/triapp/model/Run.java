package com.example.triapp.model;

public class Run {
    private Long id;
    private Long workoutId;   // FK to workout.id
    private Double distanceMiles;
    private Integer avgPaceSecPerMile; // seconds per mile
    private Integer elevationGainFeet;
    private Integer steps;
    private Integer avgCadenceSpm; // steps per minute

    public Run() {}
    public Run(Long id, Long workoutId, Double distanceMiles, Integer avgPaceSecPerMile,
               Integer elevationGainFeet, Integer steps, Integer avgCadenceSpm) {
        this.id = id; this.workoutId = workoutId; this.distanceMiles = distanceMiles;
        this.avgPaceSecPerMile = avgPaceSecPerMile; this.elevationGainFeet = elevationGainFeet;
        this.steps = steps; this.avgCadenceSpm = avgCadenceSpm;
    }

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
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

    @Override
    public String toString() {
        return "Run{" + "id=" + id + ", workoutId=" + workoutId + ", distanceMiles=" + distanceMiles +
                ", avgPaceSecPerMile=" + avgPaceSecPerMile + ", elevationGainFeet=" + elevationGainFeet +
                ", steps=" + steps + ", avgCadenceSpm=" + avgCadenceSpm + '}';
    }
}