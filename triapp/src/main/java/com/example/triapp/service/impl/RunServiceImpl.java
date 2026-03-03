package com.example.triapp.service.impl;

import com.example.triapp.model.Run;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import com.example.triapp.data.DataProvider;

import com.example.triapp.service.RunService;

public class RunServiceImpl implements RunService{

    private final DataProvider dataProvider;

    public RunServiceImpl(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @Override
    public List<Run> findByFilters(
            Long id,
            Long workoutId,
            Double distanceMiles,
            Double minDistanceMiles,
            Double maxDistanceMiles,
            Integer avgPaceSecPerMile,
            Integer minAvgPaceSecPerMile,
            Integer maxAvgPaceSecPerMile,
            Integer elevationGainFeet,
            Integer minElevationGainFeet,
            Integer maxElevationGainFeet,
            Integer steps,
            Integer minSteps,
            Integer maxSteps,
            Integer avgCadenceSpm,
            Integer minAvgCadenceSpm,
            Integer maxAvgCadenceSpm
    ) {
        try {
            List<Run> base = dataProvider.readAllRuns();
    
            return base.stream()
                    .filter(r -> {
                        if (id != null) {
                            Long v = r.getId(); if (v == null || !id.equals(v)) return false;
                        }
                        if (workoutId != null) {
                            Long v = r.getWorkoutId(); if (v == null || !workoutId.equals(v)) return false;
                        }
                        if (distanceMiles != null) {
                            Double v = r.getDistanceMiles(); if (v == null || !distanceMiles.equals(v)) return false;
                        }
                        if (minDistanceMiles != null) {
                            Double v = r.getDistanceMiles(); if (v == null || v < minDistanceMiles) return false;
                        }
                        if (maxDistanceMiles != null) {
                            Double v = r.getDistanceMiles(); if (v == null || v > maxDistanceMiles) return false;
                        }
                        if (avgPaceSecPerMile != null) {
                            Integer v = r.getAvgPaceSecPerMile(); if (v == null || !avgPaceSecPerMile.equals(v)) return false;
                        }
                        if (minAvgPaceSecPerMile != null) {
                            Integer v = r.getAvgPaceSecPerMile(); if (v == null || v < minAvgPaceSecPerMile) return false;
                        }
                        if (maxAvgPaceSecPerMile != null) {
                            Integer v = r.getAvgPaceSecPerMile(); if (v == null || v > maxAvgPaceSecPerMile) return false;
                        }
                        if (elevationGainFeet != null) {
                            Integer v = r.getElevationGainFeet(); if (v == null || !elevationGainFeet.equals(v)) return false;
                        }
                        if (minElevationGainFeet != null) {
                            Integer v = r.getElevationGainFeet(); if (v == null || v < minElevationGainFeet) return false;
                        }
                        if (maxElevationGainFeet != null) {
                            Integer v = r.getElevationGainFeet(); if (v == null || v > maxElevationGainFeet) return false;
                        }
                        if (steps != null) {
                            Integer v = r.getSteps(); if (v == null || !steps.equals(v)) return false;
                        }
                        if (minSteps != null) {
                            Integer v = r.getSteps(); if (v == null || v < minSteps) return false;
                        }
                        if (maxSteps != null) {
                            Integer v = r.getSteps(); if (v == null || v > maxSteps) return false;
                        }
                        if (avgCadenceSpm != null) {
                            Integer v = r.getAvgCadenceSpm(); if (v == null || !avgCadenceSpm.equals(v)) return false;
                        }
                        if (minAvgCadenceSpm != null) {
                            Integer v = r.getAvgCadenceSpm(); if (v == null || v < minAvgCadenceSpm) return false;
                        }
                        if (maxAvgCadenceSpm != null) {
                            Integer v = r.getAvgCadenceSpm(); if (v == null || v > maxAvgCadenceSpm) return false;
                        }
                        return true;
                    })
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to read filtered runs", e);
        }
    }
    
}

