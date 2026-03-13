package com.example.triapp.service;

import java.util.List;
import com.example.triapp.model.Run;

public interface RunService {
    Run save(Run run);          // create
    Run update(Run run);        // update
    boolean delete(long id);    // delete
    Run getById(long id);       // read by id
    List<Run> getAll();         // read all
    List<Run> findByFilters(
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
    );
}
