package com.example.triapp.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.triapp.data.DataProvider;
import com.example.triapp.model.Swim;
import com.example.triapp.service.SwimService;

@Service
public class SwimServiceImpl implements SwimService {

    private final DataProvider dataProvider;

    public SwimServiceImpl(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @Override
    @Transactional
    public Swim save(Swim swim) {
        try {
            long id = dataProvider.createSwim(swim);
            return dataProvider.readSwimById(id);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create swim", e);
        }
    }

    @Override
    @Transactional
    public Swim update(Swim swim) {
        try {
            boolean ok = dataProvider.updateSwim(swim);
            if (!ok) throw new RuntimeException("Swim update returned false");
            return dataProvider.readSwimById(swim.getId());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update swim", e);
        }
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        try {
            return dataProvider.deleteSwim(id);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete swim", e);
        }
    }

    @Override
    public Swim getById(long id) {
        try {
            return dataProvider.readSwimById(id);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to read swim by id", e);
        }
    }

    @Override
    public List<Swim> getAll() {
        try {
            return dataProvider.readAllSwims();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to read all swims", e);
        }
    }

    @Override
    public List<Swim> findByFilters(
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
    ) {
        try {
            List<Swim> base = dataProvider.readAllSwims();

            return base.stream()
                    .filter(s -> {
                        // id (exact)
                        if (id != null) {
                            Long sid = s.getId();
                            if (sid == null || !id.equals(sid)) return false;
                        }

                        // workoutId (exact)
                        if (workoutId != null) {
                            Long wid = s.getWorkoutId();
                            if (wid == null || !workoutId.equals(wid)) return false;
                        }

                        // distanceYards: exact or range
                        if (distanceYards != null) {
                            Integer v = s.getDistanceYards();
                            if (v == null || !distanceYards.equals(v)) return false;
                        }
                        if (minDistanceYards != null) {
                            Integer v = s.getDistanceYards();
                            if (v == null || v < minDistanceYards) return false;
                        }
                        if (maxDistanceYards != null) {
                            Integer v = s.getDistanceYards();
                            if (v == null || v > maxDistanceYards) return false;
                        }

                        // avgPaceSecPer100Y: exact or range
                        if (avgPaceSecPer100Y != null) {
                            Integer v = s.getAvgPaceSecPer100Y();
                            if (v == null || !avgPaceSecPer100Y.equals(v)) return false;
                        }
                        if (minAvgPaceSecPer100Y != null) {
                            Integer v = s.getAvgPaceSecPer100Y();
                            if (v == null || v < minAvgPaceSecPer100Y) return false;
                        }
                        if (maxAvgPaceSecPer100Y != null) {
                            Integer v = s.getAvgPaceSecPer100Y();
                            if (v == null || v > maxAvgPaceSecPer100Y) return false;
                        }

                        // avgStrokeRateSpm: exact or range
                        if (avgStrokeRateSpm != null) {
                            Integer v = s.getAvgStrokeRateSpm();
                            if (v == null || !avgStrokeRateSpm.equals(v)) return false;
                        }
                        if (minAvgStrokeRateSpm != null) {
                            Integer v = s.getAvgStrokeRateSpm();
                            if (v == null || v < minAvgStrokeRateSpm) return false;
                        }
                        if (maxAvgStrokeRateSpm != null) {
                            Integer v = s.getAvgStrokeRateSpm();
                            if (v == null || v > maxAvgStrokeRateSpm) return false;
                        }

                        return true;
                    })
                    .collect(Collectors.toList());

        } catch (SQLException e) {
            throw new RuntimeException("Failed to read filtered swims", e);
        }
    }
}
