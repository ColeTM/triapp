package com.example.triapp.model;

import java.util.List;

public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password; // store hashed in production
    private Integer heightInches;
    private Double weightPounds;
    // When loading via DataProvider, you can optionally populate workouts
    private List<Workout> workouts;

    public User() {}
    public User(Long id, String firstName, String lastName, String email, String password,
                Integer heightInches, Double weightPounds) {
        this.id = id; this.firstName = firstName; this.lastName = lastName;
        this.email = email; this.password = password;
        this.heightInches = heightInches; this.weightPounds = weightPounds;
    }
    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Integer getHeightInches() { return heightInches; }
    public void setHeightInches(Integer heightInches) { this.heightInches = heightInches; }
    public Double getWeightPounds() { return weightPounds; }
    public void setWeightPounds(Double weightPounds) { this.weightPounds = weightPounds; }
    public List<Workout> getWorkouts() { return workouts; }
    public void setWorkouts(List<Workout> workouts) { this.workouts = workouts; }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' + ", email='" + email + '\'' +
                ", heightInches=" + heightInches + ", weightPounds=" + weightPounds + '}';
    }
}
