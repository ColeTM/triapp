CREATE DATABASE IF NOT EXISTS triapp_db;
USE triapp_db;

-- users table
CREATE TABLE users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(100) NOT NULL,
  last_name VARCHAR(100) NOT NULL,
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  height_inches INT,
  weight_pounds DOUBLE,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- workouts table (common fields)
CREATE TABLE workouts (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  workout_type ENUM('RUN','BIKE','SWIM') NOT NULL,
  date DATETIME NOT NULL,
  moving_time_sec INT,
  elapsed_time_sec INT,
  avg_heart_rate INT,
  calories INT,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- runs table (one-to-one with workouts for RUN)
CREATE TABLE runs (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  workout_id BIGINT NOT NULL UNIQUE,
  distance_miles DOUBLE,
  avg_pace_sec_per_mile INT,
  elevation_gain_feet INT,
  steps INT,
  avg_cadence_spm INT,
  FOREIGN KEY (workout_id) REFERENCES workouts(id) ON DELETE CASCADE
);

-- bikes table (one-to-one with workouts for BIKE)
CREATE TABLE bikes (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  workout_id BIGINT NOT NULL UNIQUE,
  distance_miles DOUBLE,
  avg_speed_mph DOUBLE,
  max_speed_mph DOUBLE,
  elevation_gain_feet INT,
  avg_pedal_rate_rpm INT,
  FOREIGN KEY (workout_id) REFERENCES workouts(id) ON DELETE CASCADE
);

-- swims table (one-to-one with workouts for SWIM)
CREATE TABLE swims (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  workout_id BIGINT NOT NULL UNIQUE,
  distance_yards INT,
  avg_pace_sec_per_100y INT,
  avg_stroke_rate_spm INT,
  FOREIGN KEY (workout_id) REFERENCES workouts(id) ON DELETE CASCADE
);