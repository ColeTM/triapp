package com.example.triapp.controller;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.triapp.dto.workout.CreateWorkoutRequest;
import com.example.triapp.dto.workout.UpdateWorkoutRequest;
import com.example.triapp.dto.workout.WorkoutDto;
import com.example.triapp.mapper.ApiMapper;
import com.example.triapp.model.Workout;
import com.example.triapp.service.WorkoutService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/workouts")
public class WorkoutController {

    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) { this.workoutService = workoutService; }

    @PostMapping
    public ResponseEntity<WorkoutDto> createWorkout(@Valid @RequestBody CreateWorkoutRequest req) {
        Workout w = ApiMapper.toWorkout(req);
        // Use saveWithSubtype so the Run/Bike/Swim record is created in the same transaction
        Workout created = workoutService.saveWithSubtype(w, req);
        return new ResponseEntity<>(ApiMapper.toWorkoutDto(created), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutDto> getWorkout(@PathVariable long id) {
        Workout w = workoutService.getById(id);
        if (w == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ApiMapper.toWorkoutDto(w));
    }

    @GetMapping
    public ResponseEntity<List<WorkoutDto>> getAllWorkouts(
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "workoutType", required = false) String workoutType,
            @RequestParam(value = "dateFrom", required = false) String dateFromStr,
            @RequestParam(value = "dateTo", required = false) String dateToStr,
            @RequestParam(value = "movingTimeSec", required = false) Integer movingTimeSec,
            @RequestParam(value = "minMovingTimeSec", required = false) Integer minMovingTimeSec,
            @RequestParam(value = "maxMovingTimeSec", required = false) Integer maxMovingTimeSec,
            @RequestParam(value = "elapsedTimeSec", required = false) Integer elapsedTimeSec,
            @RequestParam(value = "minElapsedTimeSec", required = false) Integer minElapsedTimeSec,
            @RequestParam(value = "maxElapsedTimeSec", required = false) Integer maxElapsedTimeSec,
            @RequestParam(value = "avgHeartRate", required = false) Integer avgHeartRate,
            @RequestParam(value = "minAvgHeartRate", required = false) Integer minAvgHeartRate,
            @RequestParam(value = "maxAvgHeartRate", required = false) Integer maxAvgHeartRate,
            @RequestParam(value = "calories", required = false) Integer calories,
            @RequestParam(value = "minCalories", required = false) Integer minCalories,
            @RequestParam(value = "maxCalories", required = false) Integer maxCalories
    ) {
        boolean anyFilterPresent = id != null || userId != null || workoutType != null ||
                dateFromStr != null || dateToStr != null ||
                movingTimeSec != null || minMovingTimeSec != null || maxMovingTimeSec != null ||
                elapsedTimeSec != null || minElapsedTimeSec != null || maxElapsedTimeSec != null ||
                avgHeartRate != null || minAvgHeartRate != null || maxAvgHeartRate != null ||
                calories != null || minCalories != null || maxCalories != null;

        if (!anyFilterPresent) {
            List<WorkoutDto> dtos = workoutService.getAll().stream()
                    .map(ApiMapper::toWorkoutDto).collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        }

        LocalDateTime dateFrom = null;
        LocalDateTime dateTo = null;
        try {
            if (dateFromStr != null) {
                if (dateFromStr.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
                    dateFrom = LocalDate.parse(dateFromStr, DateTimeFormatter.ISO_DATE).atStartOfDay();
                } else {
                    dateFrom = LocalDateTime.parse(dateFromStr, DateTimeFormatter.ISO_DATE_TIME);
                }
            }
            if (dateToStr != null) {
                if (dateToStr.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
                    dateTo = LocalDate.parse(dateToStr, DateTimeFormatter.ISO_DATE).atTime(23, 59, 59);
                } else {
                    dateTo = LocalDateTime.parse(dateToStr, DateTimeFormatter.ISO_DATE_TIME);
                }
            }
        } catch (DateTimeException ex) {
            return ResponseEntity.badRequest().build();
        }

        List<WorkoutDto> dtos = workoutService.findByFilters(
                id, userId, workoutType, dateFrom, dateTo,
                movingTimeSec, minMovingTimeSec, maxMovingTimeSec,
                elapsedTimeSec, minElapsedTimeSec, maxElapsedTimeSec,
                avgHeartRate, minAvgHeartRate, maxAvgHeartRate,
                calories, minCalories, maxCalories
        ).stream().map(ApiMapper::toWorkoutDto).collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkoutDto> updateWorkout(@PathVariable long id,
                                                    @Valid @RequestBody UpdateWorkoutRequest req) {
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
