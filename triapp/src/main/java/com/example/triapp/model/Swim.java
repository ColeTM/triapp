package com.example.triapp.model;

public class Swim {
    private Long id;
    private Long workoutId;
    private Integer distanceYards;
    private Integer avgPaceSecPer100Y; // seconds per 100 yards
    private Integer avgStrokeRateSpm;  // strokes per minute

    public Swim() {}
    public Swim(Long id, Long workoutId, Integer distanceYards, Integer avgPaceSecPer100Y, Integer avgStrokeRateSpm) {
        this.id = id; this.workoutId = workoutId; this.distanceYards = distanceYards;
        this.avgPaceSecPer100Y = avgPaceSecPer100Y; this.avgStrokeRateSpm = avgStrokeRateSpm;
    }

    // getters/setters
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

    @Override
    public String toString() {
        return "Swim{" + "id=" + id + ", workoutId=" + workoutId + ", distanceYards=" + distanceYards +
                ", avgPaceSecPer100Y=" + avgPaceSecPer100Y + ", avgStrokeRateSpm=" + avgStrokeRateSpm + '}';
    }
}
