package com.example.triapp.client;

import java.net.URI;
import java.net.http.*;
import java.time.Duration;
import java.time.LocalDateTime;
// import java.util.concurrent.CompletableFuture;
// import java.util.Random;

public class ApiTestClient {

    private static final HttpClient http = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();

    private static final String BASE = "http://localhost:8080/api";

    public static void main(String[] args) throws Exception {
        System.out.println("=== API TEST CLIENT ===");

        // 1) Create a user
        String random = String.valueOf(System.currentTimeMillis() % 100000);
        String createUserJson = String.format(
                "{\"firstName\":\"Api\",\"lastName\":\"Tester\",\"email\":\"api.tester.%s@example.com\",\"password\":\"pw12345\",\"heightInches\":70,\"weightPounds\":175.0}",
                random);

        HttpRequest createUserReq = HttpRequest.newBuilder()
                .uri(URI.create(BASE + "/users"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(createUserJson))
                .build();

        HttpResponse<String> createUserResp = http.send(createUserReq, HttpResponse.BodyHandlers.ofString());
        System.out.println("Create user status: " + createUserResp.statusCode());
        System.out.println("Create user response: " + createUserResp.body());

        // Parse the created user's id (naive parse)
        Long userId = parseIdFromJson(createUserResp.body());
        System.out.println("Created user id: " + userId);

        // 2) Create a workout for that user
        String createWorkoutJson = String.format(
                "{\"userId\":%d,\"workoutType\":\"RUN\",\"date\":\"%s\",\"movingTimeSec\":3600,\"elapsedTimeSec\":3620,\"avgHeartRate\":150,\"calories\":600}",
                userId, LocalDateTime.now().toString());

        HttpRequest createWorkoutReq = HttpRequest.newBuilder()
                .uri(URI.create(BASE + "/workouts"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(createWorkoutJson))
                .build();

        HttpResponse<String> createWorkoutResp = http.send(createWorkoutReq, HttpResponse.BodyHandlers.ofString());
        System.out.println("Create workout status: " + createWorkoutResp.statusCode());
        System.out.println("Create workout response: " + createWorkoutResp.body());
        Long workoutId = parseIdFromJson(createWorkoutResp.body());
        System.out.println("Created workout id: " + workoutId);

        // 3) Read back the workout
        HttpRequest getWorkoutReq = HttpRequest.newBuilder()
                .uri(URI.create(BASE + "/workouts/" + workoutId))
                .GET().build();

        HttpResponse<String> getWorkoutResp = http.send(getWorkoutReq, HttpResponse.BodyHandlers.ofString());
        System.out.println("Get workout status: " + getWorkoutResp.statusCode());
        System.out.println("Get workout body: " + getWorkoutResp.body());

        // 4) Update workout (change calories)
        String updateWorkoutJson = String.format("{\"calories\":%d}", 650);
        HttpRequest updateWorkoutReq = HttpRequest.newBuilder()
                .uri(URI.create(BASE + "/workouts/" + workoutId))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(updateWorkoutJson))
                .build();

        HttpResponse<String> updateWorkoutResp = http.send(updateWorkoutReq, HttpResponse.BodyHandlers.ofString());
        System.out.println("Update workout status: " + updateWorkoutResp.statusCode());
        System.out.println("Update workout body: " + updateWorkoutResp.body());

        // 5) Delete workout
        HttpRequest deleteWorkoutReq = HttpRequest.newBuilder()
                .uri(URI.create(BASE + "/workouts/" + workoutId))
                .DELETE().build();

        HttpResponse<String> deleteWorkoutResp = http.send(deleteWorkoutReq, HttpResponse.BodyHandlers.ofString());
        System.out.println("Delete workout status: " + deleteWorkoutResp.statusCode());

        // 6) Delete user
        HttpRequest deleteUserReq = HttpRequest.newBuilder()
                .uri(URI.create(BASE + "/users/" + userId))
                .DELETE().build();

        HttpResponse<String> deleteUserResp = http.send(deleteUserReq, HttpResponse.BodyHandlers.ofString());
        System.out.println("Delete user status: " + deleteUserResp.statusCode());

        System.out.println("=== API TEST CLIENT COMPLETE ===");
    }

    private static Long parseIdFromJson(String json) {
        // naive: look for "id":NUMBER
        if (json == null) return null;
        int idx = json.indexOf("\"id\":");
        if (idx == -1) return null;
        int start = idx + 5;
        int end = start;
        while (end < json.length() && (Character.isDigit(json.charAt(end)) || json.charAt(end) == '-')) end++;
        try {
            return Long.parseLong(json.substring(start, end));
        } catch (Exception e) {
            return null;
        }
    }
}