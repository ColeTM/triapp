package com.example.triapp.dto.user;

public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Integer heightInches;
    private Double weightPounds;

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Integer getHeightInches() { return heightInches; }
    public void setHeightInches(Integer heightInches) { this.heightInches = heightInches; }
    public Double getWeightPounds() { return weightPounds; }
    public void setWeightPounds(Double weightPounds) { this.weightPounds = weightPounds; }
}