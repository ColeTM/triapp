package com.example.triapp.controller;

import com.example.triapp.dto.run.*;
import com.example.triapp.mapper.ApiMapper;
import com.example.triapp.model.Run;
import com.example.triapp.service.RunService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/runs")
public class RunController {

    private final RunService runService;

    public RunController(RunService runService) {
        this.runService = runService;
    }

    @PostMapping
    public ResponseEntity<RunDto> createRun(@Valid @RequestBody CreateRunRequest req) {
        Run run = ApiMapper.toRun(req);
        Run created = runService.save(run);
        return new ResponseEntity<>(ApiMapper.toRunDto(created), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RunDto> getRun(@PathVariable long id) {
        Run r = runService.getById(id);
        if (r == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ApiMapper.toRunDto(r));
    }

    @GetMapping
    public ResponseEntity<List<RunDto>> getAllRuns(
            @RequestParam(value = "id",                   required = false) Long id,
            @RequestParam(value = "workoutId",            required = false) Long workoutId,
            @RequestParam(value = "distanceMiles",        required = false) Double distanceMiles,
            @RequestParam(value = "minDistanceMiles",     required = false) Double minDistanceMiles,
            @RequestParam(value = "maxDistanceMiles",     required = false) Double maxDistanceMiles,
            @RequestParam(value = "avgPaceSecPerMile",    required = false) Integer avgPaceSecPerMile,
            @RequestParam(value = "minAvgPaceSecPerMile", required = false) Integer minAvgPaceSecPerMile,
            @RequestParam(value = "maxAvgPaceSecPerMile", required = false) Integer maxAvgPaceSecPerMile,
            @RequestParam(value = "elevationGainFeet",    required = false) Integer elevationGainFeet,
            @RequestParam(value = "minElevationGainFeet", required = false) Integer minElevationGainFeet,
            @RequestParam(value = "maxElevationGainFeet", required = false) Integer maxElevationGainFeet,
            @RequestParam(value = "steps",                required = false) Integer steps,
            @RequestParam(value = "minSteps",             required = false) Integer minSteps,
            @RequestParam(value = "maxSteps",             required = false) Integer maxSteps,
            @RequestParam(value = "avgCadenceSpm",        required = false) Integer avgCadenceSpm,
            @RequestParam(value = "minAvgCadenceSpm",     required = false) Integer minAvgCadenceSpm,
            @RequestParam(value = "maxAvgCadenceSpm",     required = false) Integer maxAvgCadenceSpm
    ) {
        boolean anyFilter = id != null || workoutId != null ||
                distanceMiles != null || minDistanceMiles != null || maxDistanceMiles != null ||
                avgPaceSecPerMile != null || minAvgPaceSecPerMile != null || maxAvgPaceSecPerMile != null ||
                elevationGainFeet != null || minElevationGainFeet != null || maxElevationGainFeet != null ||
                steps != null || minSteps != null || maxSteps != null ||
                avgCadenceSpm != null || minAvgCadenceSpm != null || maxAvgCadenceSpm != null;

        if (!anyFilter) {
            List<RunDto> dtos = runService.getAll().stream()
                    .map(ApiMapper::toRunDto).collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        }

        List<RunDto> dtos = runService.findByFilters(
                id, workoutId,
                distanceMiles, minDistanceMiles, maxDistanceMiles,
                avgPaceSecPerMile, minAvgPaceSecPerMile, maxAvgPaceSecPerMile,
                elevationGainFeet, minElevationGainFeet, maxElevationGainFeet,
                steps, minSteps, maxSteps,
                avgCadenceSpm, minAvgCadenceSpm, maxAvgCadenceSpm
        ).stream().map(ApiMapper::toRunDto).collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RunDto> updateRun(@PathVariable long id, @RequestBody UpdateRunRequest req) {
        Run existing = runService.getById(id);
        if (existing == null) return ResponseEntity.notFound().build();
        ApiMapper.updateRunFrom(req, existing);
        Run updated = runService.update(existing);
        return ResponseEntity.ok(ApiMapper.toRunDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRun(@PathVariable long id) {
        boolean ok = runService.delete(id);
        if (!ok) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }
}
