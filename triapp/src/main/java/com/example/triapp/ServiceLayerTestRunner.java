package com.example.triapp;

import com.example.triapp.model.User;
import com.example.triapp.model.Workout;
import com.example.triapp.service.UserService;
import com.example.triapp.service.WorkoutService;
import org.springframework.boot.CommandLineRunner;
// import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ServiceLayerTestRunner - quick console runner to exercise UserService and WorkoutService
 */
// @Component
public class ServiceLayerTestRunner implements CommandLineRunner {

    private final UserService userService;
    private final WorkoutService workoutService;

    public ServiceLayerTestRunner(UserService userService, WorkoutService workoutService) {
        this.userService = userService;
        this.workoutService = workoutService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== SERVICE LAYER TEST RUNNER ===");

        // ---------- Create user ----------
        User u = new User();
        u.setFirstName("Svc");
        u.setLastName("Tester");
        // Use timestamped email to avoid UNIQUE collisions across runs
        u.setEmail("svc_tester_" + System.currentTimeMillis() + "@example.com");
        u.setPassword("pw123");
        u.setHeightInches(71);
        u.setWeightPounds(180.0);

        System.out.println("-- Creating user --");
        User createdUser = userService.save(u);
        System.out.println("Created user: " + createdUser);

        // ---------- Read user ----------
        System.out.println("-- Reading user by id --");
        User readUser = userService.getById(createdUser.getId());
        System.out.println("Read user: " + readUser);

        System.out.println("-- Listing all users (partial) --");
        List<User> users = userService.getAll();
        System.out.println("Total users: " + users.size());

        // ---------- Update user ----------
        System.out.println("-- Updating user (change last name) --");
        createdUser.setLastName("Tester-Updated");
        User updatedUser = userService.update(createdUser);
        System.out.println("Updated user: " + updatedUser);

        // ---------- Create workout ----------
        System.out.println("-- Creating workout for user --");
        Workout w = new Workout();
        w.setUserId(createdUser.getId());
        w.setWorkoutType("RUN");
        w.setDate(LocalDateTime.now());
        w.setMovingTimeSec(3600);
        w.setElapsedTimeSec(3650);
        w.setAvgHeartRate(150);
        w.setCalories(600);

        Workout createdWorkout = workoutService.save(w);
        System.out.println("Created workout: " + createdWorkout);

        // ---------- Read workout ----------
        System.out.println("-- Reading workout by id --");
        Workout readWorkout = workoutService.getById(createdWorkout.getId());
        System.out.println("Read workout: " + readWorkout);

        System.out.println("-- Listing workouts for user --");
        List<Workout> workoutsForUser = workoutService.getByUserId(createdUser.getId());
        System.out.println("Workouts for user " + createdUser.getId() + ": " + workoutsForUser.size());

        // ---------- Update workout ----------
        System.out.println("-- Updating workout (change calories) --");
        createdWorkout.setCalories(650);
        Workout updatedWorkout = workoutService.update(createdWorkout);
        System.out.println("Updated workout: " + updatedWorkout);

        // ---------- Delete workout ----------
        System.out.println("-- Deleting workout --");
        boolean removedWorkout = workoutService.delete(createdWorkout.getId());
        System.out.println("Workout deleted: " + removedWorkout);

        // Verify deletion
        Workout shouldBeNull = workoutService.getById(createdWorkout.getId());
        System.out.println("Post-delete read (should be null): " + shouldBeNull);

        // ---------- Delete user ----------
        System.out.println("-- Deleting user --");
        boolean removedUser = userService.delete(createdUser.getId());
        System.out.println("User deleted: " + removedUser);

        // Verify deletion
        User shouldBeNullUser = userService.getById(createdUser.getId());
        System.out.println("Post-delete read user (should be null): " + shouldBeNullUser);

        System.out.println("=== SERVICE LAYER TEST RUNNER COMPLETE ===");
    }
}