package com.example.triapp.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class UpdateUserRequest {
    private String firstName;
    private String lastName;
    @Email
    private String email;
    @Size(min = 6)
    private String password;
    private Integer heightInches;
    private Double weightPounds;

    // getters & setters
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
}