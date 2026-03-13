package com.example.triapp.service;

import java.util.List;
import com.example.triapp.model.Swim;

public interface SwimService {
    Swim save(Swim swim);           // create
    Swim update(Swim swim);         // update
    boolean delete(long id);        // delete
    Swim getById(long id);          // read by id
    List<Swim> getAll();            // read all
    List<Swim> findByFilters(
        Long id,
        Long workoutId,
        Integer distanceYards,
        Integer minDistanceYards,
        Integer maxDistanceYards,
        Integer avgPaceSecPer100Y,
        Integer minAvgPaceSecPer100Y,
        Integer maxAvgPaceSecPer100Y,
        Integer avgStrokeRateSpm,
        Integer minAvgStrokeRateSpm,
        Integer maxAvgStrokeRateSpm
    );
}
