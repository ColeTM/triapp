package com.example.triapp.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.triapp.data.DataProvider;
import com.example.triapp.model.Run;
import com.example.triapp.service.RunService;

@Service
public class RunServiceImpl implements RunService {

    private final DataProvider dataProvider;

    public RunServiceImpl(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @Transactional
    public Run save(Run run) {
        try {
            long id = dataProvider.createRun(run);
            return dataProvider.readRunById(id);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create run", e);
        }
    }

    @Transactional
    public Run update(Run run) {
        try {
            boolean ok = dataProvider.updateRun(run);
            if (!ok) throw new RuntimeException("Run update returned false");
            return dataProvider.readRunById(run.getId());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update run", e);
        }
    }

    @Transactional
    public boolean delete(long id) {
        try {
            return dataProvider.deleteRun(id);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete run", e);
        }
    }

    public Run getById(long id) {
        try {
            return dataProvider.readRunById(id);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to read run by id", e);
        }
    }

    public List<Run> getAll() {
        try {
            return dataProvider.readAllRuns();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to read all runs", e);
        }
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
                        // id (exact)
                        if (id != null) {
                            Long rid = r.getId();
                            if (rid == null || !id.equals(rid)) return false;
                        }

                        // workoutId (exact)
                        if (workoutId != null) {
                            Long wid = r.getWorkoutId();
                            if (wid == null || !workoutId.equals(wid)) return false;
                        }

                        // distanceMiles: exact or range
                        if (distanceMiles != null) {
                            Double v = r.getDistanceMiles();
                            if (v == null || !distanceMiles.equals(v)) return false;
                        }
                        if (minDistanceMiles != null) {
                            Double v = r.getDistanceMiles();
                            if (v == null || v < minDistanceMiles) return false;
                        }
                        if (maxDistanceMiles != null) {
                            Double v = r.getDistanceMiles();
                            if (v == null || v > maxDistanceMiles) return false;
                        }

                        // avgPaceSecPerMile: exact or range
                        if (avgPaceSecPerMile != null) {
                            Integer v = r.getAvgPaceSecPerMile();
                            if (v == null || !avgPaceSecPerMile.equals(v)) return false;
                        }
                        if (minAvgPaceSecPerMile != null) {
                            Integer v = r.getAvgPaceSecPerMile();
                            if (v == null || v < minAvgPaceSecPerMile) return false;
                        }
                        if (maxAvgPaceSecPerMile != null) {
                            Integer v = r.getAvgPaceSecPerMile();
                            if (v == null || v > maxAvgPaceSecPerMile) return false;
                        }

                        // elevationGainFeet: exact or range
                        if (elevationGainFeet != null) {
                            Integer v = r.getElevationGainFeet();
                            if (v == null || !elevationGainFeet.equals(v)) return false;
                        }
                        if (minElevationGainFeet != null) {
                            Integer v = r.getElevationGainFeet();
                            if (v == null || v < minElevationGainFeet) return false;
                        }
                        if (maxElevationGainFeet != null) {
                            Integer v = r.getElevationGainFeet();
                            if (v == null || v > maxElevationGainFeet) return false;
                        }

                        // steps: exact or range
                        if (steps != null) {
                            Integer v = r.getSteps();
                            if (v == null || !steps.equals(v)) return false;
                        }
                        if (minSteps != null) {
                            Integer v = r.getSteps();
                            if (v == null || v < minSteps) return false;
                        }
                        if (maxSteps != null) {
                            Integer v = r.getSteps();
                            if (v == null || v > maxSteps) return false;
                        }

                        // avgCadenceSpm: exact or range
                        if (avgCadenceSpm != null) {
                            Integer v = r.getAvgCadenceSpm();
                            if (v == null || !avgCadenceSpm.equals(v)) return false;
                        }
                        if (minAvgCadenceSpm != null) {
                            Integer v = r.getAvgCadenceSpm();
                            if (v == null || v < minAvgCadenceSpm) return false;
                        }
                        if (maxAvgCadenceSpm != null) {
                            Integer v = r.getAvgCadenceSpm();
                            if (v == null || v > maxAvgCadenceSpm) return false;
                        }

                        return true;
                    })
                    .collect(Collectors.toList());

        } catch (SQLException e) {
            throw new RuntimeException("Failed to read filtered runs", e);
        }
    }
}
