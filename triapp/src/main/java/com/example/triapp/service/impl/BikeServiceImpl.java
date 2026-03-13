package com.example.triapp.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.triapp.data.DataProvider;
import com.example.triapp.model.Bike;
import com.example.triapp.service.BikeService;

@Service
public class BikeServiceImpl implements BikeService {

    private final DataProvider dataProvider;

    public BikeServiceImpl(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @Override
    @Transactional
    public Bike save(Bike bike) {
        try {
            long id = dataProvider.createBike(bike);
            return dataProvider.readBikeById(id);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create bike", e);
        }
    }

    @Override
    @Transactional
    public Bike update(Bike bike) {
        try {
            boolean ok = dataProvider.updateBike(bike);
            if (!ok) throw new RuntimeException("Bike update returned false");
            return dataProvider.readBikeById(bike.getId());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update bike", e);
        }
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        try {
            return dataProvider.deleteBike(id);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete bike", e);
        }
    }

    @Override
    public Bike getById(long id) {
        try {
            return dataProvider.readBikeById(id);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to read bike by id", e);
        }
    }

    @Override
    public List<Bike> getAll() {
        try {
            return dataProvider.readAllBikes();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to read all bikes", e);
        }
    }

    @Override
    public List<Bike> findByFilters(
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
    ) {
        try {
            List<Bike> base = dataProvider.readAllBikes();

            return base.stream()
                    .filter(b -> {
                        // id (exact)
                        if (id != null) {
                            Long bid = b.getId();
                            if (bid == null || !id.equals(bid)) return false;
                        }

                        // workoutId (exact)
                        if (workoutId != null) {
                            Long wid = b.getWorkoutId();
                            if (wid == null || !workoutId.equals(wid)) return false;
                        }

                        // distanceMiles: exact or range
                        if (distanceMiles != null) {
                            Double v = b.getDistanceMiles();
                            if (v == null || !distanceMiles.equals(v)) return false;
                        }
                        if (minDistanceMiles != null) {
                            Double v = b.getDistanceMiles();
                            if (v == null || v < minDistanceMiles) return false;
                        }
                        if (maxDistanceMiles != null) {
                            Double v = b.getDistanceMiles();
                            if (v == null || v > maxDistanceMiles) return false;
                        }

                        // avgSpeedMph: exact or range
                        if (avgSpeedMph != null) {
                            Double v = b.getAvgSpeedMph();
                            if (v == null || !avgSpeedMph.equals(v)) return false;
                        }
                        if (minAvgSpeedMph != null) {
                            Double v = b.getAvgSpeedMph();
                            if (v == null || v < minAvgSpeedMph) return false;
                        }
                        if (maxAvgSpeedMph != null) {
                            Double v = b.getAvgSpeedMph();
                            if (v == null || v > maxAvgSpeedMph) return false;
                        }

                        // maxSpeedMph: exact or range
                        if (maxSpeedMph != null) {
                            Double v = b.getMaxSpeedMph();
                            if (v == null || !maxSpeedMph.equals(v)) return false;
                        }
                        if (minMaxSpeedMph != null) {
                            Double v = b.getMaxSpeedMph();
                            if (v == null || v < minMaxSpeedMph) return false;
                        }
                        if (maxMaxSpeedMph != null) {
                            Double v = b.getMaxSpeedMph();
                            if (v == null || v > maxMaxSpeedMph) return false;
                        }

                        // elevationGainFeet: exact or range
                        if (elevationGainFeet != null) {
                            Integer v = b.getElevationGainFeet();
                            if (v == null || !elevationGainFeet.equals(v)) return false;
                        }
                        if (minElevationGainFeet != null) {
                            Integer v = b.getElevationGainFeet();
                            if (v == null || v < minElevationGainFeet) return false;
                        }
                        if (maxElevationGainFeet != null) {
                            Integer v = b.getElevationGainFeet();
                            if (v == null || v > maxElevationGainFeet) return false;
                        }

                        // avgPedalRateRpm: exact or range
                        if (avgPedalRateRpm != null) {
                            Integer v = b.getAvgPedalRateRpm();
                            if (v == null || !avgPedalRateRpm.equals(v)) return false;
                        }
                        if (minAvgPedalRateRpm != null) {
                            Integer v = b.getAvgPedalRateRpm();
                            if (v == null || v < minAvgPedalRateRpm) return false;
                        }
                        if (maxAvgPedalRateRpm != null) {
                            Integer v = b.getAvgPedalRateRpm();
                            if (v == null || v > maxAvgPedalRateRpm) return false;
                        }

                        return true;
                    })
                    .collect(Collectors.toList());

        } catch (SQLException e) {
            throw new RuntimeException("Failed to read filtered bikes", e);
        }
    }
}
