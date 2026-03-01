package com.example.triapp.controller;

import com.example.triapp.dto.workout.*;
import com.example.triapp.mapper.ApiMapper;
import com.example.triapp.model.Workout;
import com.example.triapp.service.WorkoutService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/workouts")
public class WorkoutController {

    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) { this.workoutService = workoutService; }

    @PostMapping
    public ResponseEntity<WorkoutDto> createWorkout(@Valid @RequestBody CreateWorkoutRequest req) {
        Workout w = ApiMapper.toWorkout(req);
        Workout created = workoutService.save(w);
        return new ResponseEntity<>(ApiMapper.toWorkoutDto(created), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutDto> getWorkout(@PathVariable long id) {
        Workout w = workoutService.getById(id);
        if (w == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ApiMapper.toWorkoutDto(w));
    }

    @GetMapping
    public ResponseEntity<List<WorkoutDto>> getAllWorkouts(@RequestParam(value = "userId", required = false) Long userId) {
        List<WorkoutDto> dtos;
        if (userId == null) {
            dtos = workoutService.getAll().stream().map(ApiMapper::toWorkoutDto).collect(Collectors.toList());
        } else {
            dtos = workoutService.getByUserId(userId).stream().map(ApiMapper::toWorkoutDto).collect(Collectors.toList());
        }
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkoutDto> updateWorkout(@PathVariable long id, @Valid @RequestBody UpdateWorkoutRequest req) {
        Workout existing = workoutService.getById(id);
        if (existing == null) return ResponseEntity.notFound().build();
        ApiMapper.updateWorkoutFrom(req, existing);
        Workout updated = workoutService.update(existing);
        return ResponseEntity.ok(ApiMapper.toWorkoutDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable long id) {
        boolean ok = workoutService.delete(id);
        if (!ok) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }
}