package com.example.triapp.controller;

import com.example.triapp.dto.swim.*;
import com.example.triapp.mapper.ApiMapper;
import com.example.triapp.model.Swim;
import com.example.triapp.service.SwimService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/swims")
public class SwimController {

    private final SwimService swimService;

    public SwimController(SwimService swimService) {
        this.swimService = swimService;
    }

    @PostMapping
    public ResponseEntity<SwimDto> createSwim(@Valid @RequestBody CreateSwimRequest req) {
        Swim s = ApiMapper.toSwim(req);
        Swim created = swimService.save(s);
        return new ResponseEntity<>(ApiMapper.toSwimDto(created), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SwimDto> getSwim(@PathVariable long id) {
        Swim s = swimService.getById(id);
        if (s == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ApiMapper.toSwimDto(s));
    }

    @GetMapping
    public ResponseEntity<List<SwimDto>> getAllSwims(
            @RequestParam(value = "id",                   required = false) Long id,
            @RequestParam(value = "workoutId",            required = false) Long workoutId,
            @RequestParam(value = "distanceYards",        required = false) Integer distanceYards,
            @RequestParam(value = "minDistanceYards",     required = false) Integer minDistanceYards,
            @RequestParam(value = "maxDistanceYards",     required = false) Integer maxDistanceYards,
            @RequestParam(value = "avgPaceSecPer100Y",    required = false) Integer avgPaceSecPer100Y,
            @RequestParam(value = "minAvgPaceSecPer100Y", required = false) Integer minAvgPaceSecPer100Y,
            @RequestParam(value = "maxAvgPaceSecPer100Y", required = false) Integer maxAvgPaceSecPer100Y,
            @RequestParam(value = "avgStrokeRateSpm",     required = false) Integer avgStrokeRateSpm,
            @RequestParam(value = "minAvgStrokeRateSpm",  required = false) Integer minAvgStrokeRateSpm,
            @RequestParam(value = "maxAvgStrokeRateSpm",  required = false) Integer maxAvgStrokeRateSpm
    ) {
        boolean anyFilter = id != null || workoutId != null ||
                distanceYards != null || minDistanceYards != null || maxDistanceYards != null ||
                avgPaceSecPer100Y != null || minAvgPaceSecPer100Y != null || maxAvgPaceSecPer100Y != null ||
                avgStrokeRateSpm != null || minAvgStrokeRateSpm != null || maxAvgStrokeRateSpm != null;

        if (!anyFilter) {
            List<SwimDto> dtos = swimService.getAll().stream()
                    .map(ApiMapper::toSwimDto).collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        }

        List<SwimDto> dtos = swimService.findByFilters(
                id, workoutId,
                distanceYards, minDistanceYards, maxDistanceYards,
                avgPaceSecPer100Y, minAvgPaceSecPer100Y, maxAvgPaceSecPer100Y,
                avgStrokeRateSpm, minAvgStrokeRateSpm, maxAvgStrokeRateSpm
        ).stream().map(ApiMapper::toSwimDto).collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SwimDto> updateSwim(@PathVariable long id, @RequestBody UpdateSwimRequest req) {
        Swim existing = swimService.getById(id);
        if (existing == null) return ResponseEntity.notFound().build();
        ApiMapper.updateSwimFrom(req, existing);
        Swim updated = swimService.update(existing);
        return ResponseEntity.ok(ApiMapper.toSwimDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSwim(@PathVariable long id) {
        boolean ok = swimService.delete(id);
        if (!ok) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }
}
