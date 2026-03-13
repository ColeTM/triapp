package com.example.triapp.controller;

import com.example.triapp.dto.bike.*;
import com.example.triapp.mapper.ApiMapper;
import com.example.triapp.model.Bike;
import com.example.triapp.service.BikeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bikes")
public class BikeController {

    private final BikeService bikeService;

    public BikeController(BikeService bikeService) {
        this.bikeService = bikeService;
    }

    @PostMapping
    public ResponseEntity<BikeDto> createBike(@Valid @RequestBody CreateBikeRequest req) {
        Bike b = ApiMapper.toBike(req);
        Bike created = bikeService.save(b);
        return new ResponseEntity<>(ApiMapper.toBikeDto(created), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BikeDto> getBike(@PathVariable long id) {
        Bike b = bikeService.getById(id);
        if (b == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ApiMapper.toBikeDto(b));
    }

    @GetMapping
    public ResponseEntity<List<BikeDto>> getAllBikes(
            @RequestParam(value = "id",                 required = false) Long id,
            @RequestParam(value = "workoutId",          required = false) Long workoutId,
            @RequestParam(value = "distanceMiles",      required = false) Double distanceMiles,
            @RequestParam(value = "minDistanceMiles",   required = false) Double minDistanceMiles,
            @RequestParam(value = "maxDistanceMiles",   required = false) Double maxDistanceMiles,
            @RequestParam(value = "avgSpeedMph",        required = false) Double avgSpeedMph,
            @RequestParam(value = "minAvgSpeedMph",     required = false) Double minAvgSpeedMph,
            @RequestParam(value = "maxAvgSpeedMph",     required = false) Double maxAvgSpeedMph,
            @RequestParam(value = "maxSpeedMph",        required = false) Double maxSpeedMph,
            @RequestParam(value = "minMaxSpeedMph",     required = false) Double minMaxSpeedMph,
            @RequestParam(value = "maxMaxSpeedMph",     required = false) Double maxMaxSpeedMph,
            @RequestParam(value = "elevationGainFeet",    required = false) Integer elevationGainFeet,
            @RequestParam(value = "minElevationGainFeet", required = false) Integer minElevationGainFeet,
            @RequestParam(value = "maxElevationGainFeet", required = false) Integer maxElevationGainFeet,
            @RequestParam(value = "avgPedalRateRpm",    required = false) Integer avgPedalRateRpm,
            @RequestParam(value = "minAvgPedalRateRpm", required = false) Integer minAvgPedalRateRpm,
            @RequestParam(value = "maxAvgPedalRateRpm", required = false) Integer maxAvgPedalRateRpm
    ) {
        boolean anyFilter = id != null || workoutId != null ||
                distanceMiles != null || minDistanceMiles != null || maxDistanceMiles != null ||
                avgSpeedMph != null || minAvgSpeedMph != null || maxAvgSpeedMph != null ||
                maxSpeedMph != null || minMaxSpeedMph != null || maxMaxSpeedMph != null ||
                elevationGainFeet != null || minElevationGainFeet != null || maxElevationGainFeet != null ||
                avgPedalRateRpm != null || minAvgPedalRateRpm != null || maxAvgPedalRateRpm != null;

        if (!anyFilter) {
            List<BikeDto> dtos = bikeService.getAll().stream()
                    .map(ApiMapper::toBikeDto).collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        }

        List<BikeDto> dtos = bikeService.findByFilters(
                id, workoutId,
                distanceMiles, minDistanceMiles, maxDistanceMiles,
                avgSpeedMph, minAvgSpeedMph, maxAvgSpeedMph,
                maxSpeedMph, minMaxSpeedMph, maxMaxSpeedMph,
                elevationGainFeet, minElevationGainFeet, maxElevationGainFeet,
                avgPedalRateRpm, minAvgPedalRateRpm, maxAvgPedalRateRpm
        ).stream().map(ApiMapper::toBikeDto).collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BikeDto> updateBike(@PathVariable long id, @RequestBody UpdateBikeRequest req) {
        Bike existing = bikeService.getById(id);
        if (existing == null) return ResponseEntity.notFound().build();
        ApiMapper.updateBikeFrom(req, existing);
        Bike updated = bikeService.update(existing);
        return ResponseEntity.ok(ApiMapper.toBikeDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBike(@PathVariable long id) {
        boolean ok = bikeService.delete(id);
        if (!ok) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }
}
