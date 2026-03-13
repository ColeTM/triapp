package com.example.triapp.service;

import java.util.List;
import com.example.triapp.model.Bike;

public interface BikeService {
    Bike save(Bike bike);           // create
    Bike update(Bike bike);         // update
    boolean delete(long id);        // delete
    Bike getById(long id);          // read by id
    List<Bike> getAll();            // read all
    List<Bike> findByFilters(
        Long id,
        Long workoutId,
        Double distanceMiles,
        Double minDistanceMiles,
        Double maxDistanceMiles,
        Double avgSpeedMph,
        Double minAvgSpeedMph,
        Double maxAvgSpeedMph,
        Double maxSpeedMph,
        Double minMaxSpeedMph,
        Double maxMaxSpeedMph,
        Integer elevationGainFeet,
        Integer minElevationGainFeet,
        Integer maxElevationGainFeet,
        Integer avgPedalRateRpm,
        Integer minAvgPedalRateRpm,
        Integer maxAvgPedalRateRpm
    );
}
