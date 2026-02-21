package com.example.triapp.data;

import com.example.triapp.model.*;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DataProvider - plain JDBC DAO using a Spring-injected DataSource.
 *
 * Notes:
 *  - Methods throw SQLException; you can wrap them in a custom DAO exception if desired.
 *  - All connections are obtained from the injected DataSource and closed automatically by try-with-resources.
 *  - Dates use LocalDateTime and map to SQL Timestamp.
 */
@Repository
public class DataProvider {

    private final DataSource dataSource;

    public DataProvider(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    // ---------------------------
    // Users CRUD
    // ---------------------------
    public long createUser(User user) throws SQLException {
        String sql = "INSERT INTO users (first_name, last_name, email, password, height_inches, weight_pounds) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            if (user.getHeightInches() != null) ps.setInt(5, user.getHeightInches()); else ps.setNull(5, Types.INTEGER);
            if (user.getWeightPounds() != null) ps.setDouble(6, user.getWeightPounds()); else ps.setNull(6, Types.DOUBLE);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    long id = rs.getLong(1);
                    user.setId(id);
                    return id;
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        }
    }

    public User readUserById(long id) throws SQLException {
        String sql = "SELECT id, first_name, last_name, email, password, height_inches, weight_pounds FROM users WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User u = new User();
                    u.setId(rs.getLong("id"));
                    u.setFirstName(rs.getString("first_name"));
                    u.setLastName(rs.getString("last_name"));
                    u.setEmail(rs.getString("email"));
                    u.setPassword(rs.getString("password"));
                    int h = rs.getInt("height_inches");
                    if (!rs.wasNull()) u.setHeightInches(h);
                    double w = rs.getDouble("weight_pounds");
                    if (!rs.wasNull()) u.setWeightPounds(w);
                    return u;
                } else {
                    return null;
                }
            }
        }
    }

    public List<User> readAllUsers() throws SQLException {
        String sql = "SELECT id, first_name, last_name, email, password, height_inches, weight_pounds FROM users";
        List<User> out = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getLong("id"));
                u.setFirstName(rs.getString("first_name"));
                u.setLastName(rs.getString("last_name"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                int h = rs.getInt("height_inches");
                if (!rs.wasNull()) u.setHeightInches(h);
                double w = rs.getDouble("weight_pounds");
                if (!rs.wasNull()) u.setWeightPounds(w);
                out.add(u);
            }
        }
        return out;
    }

    public boolean updateUser(User user) throws SQLException {
        String sql = "UPDATE users SET first_name=?, last_name=?, email=?, password=?, height_inches=?, weight_pounds=? WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            if (user.getHeightInches() != null) ps.setInt(5, user.getHeightInches()); else ps.setNull(5, Types.INTEGER);
            if (user.getWeightPounds() != null) ps.setDouble(6, user.getWeightPounds()); else ps.setNull(6, Types.DOUBLE);
            ps.setLong(7, user.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteUser(long id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // ---------------------------
    // Workouts CRUD
    // ---------------------------

    public long createWorkout(Workout w) throws SQLException {
        String sql = "INSERT INTO workouts (user_id, workout_type, date, moving_time_sec, elapsed_time_sec, avg_heart_rate, calories) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, w.getUserId());
            ps.setString(2, w.getWorkoutType());
            ps.setTimestamp(3, Timestamp.valueOf(w.getDate()));
            if (w.getMovingTimeSec() != null) ps.setInt(4, w.getMovingTimeSec()); else ps.setNull(4, Types.INTEGER);
            if (w.getElapsedTimeSec() != null) ps.setInt(5, w.getElapsedTimeSec()); else ps.setNull(5, Types.INTEGER);
            if (w.getAvgHeartRate() != null) ps.setInt(6, w.getAvgHeartRate()); else ps.setNull(6, Types.INTEGER);
            if (w.getCalories() != null) ps.setInt(7, w.getCalories()); else ps.setNull(7, Types.INTEGER);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    long id = rs.getLong(1);
                    w.setId(id);
                    return id;
                } else {
                    throw new SQLException("Creating workout failed, no ID obtained.");
                }
            }
        }
    }

    public Workout readWorkoutById(long id) throws SQLException {
        String sql = "SELECT id, user_id, workout_type, date, moving_time_sec, elapsed_time_sec, avg_heart_rate, calories FROM workouts WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Workout w = new Workout();
                    w.setId(rs.getLong("id"));
                    w.setUserId(rs.getLong("user_id"));
                    w.setWorkoutType(rs.getString("workout_type"));
                    Timestamp t = rs.getTimestamp("date");
                    if (t != null) w.setDate(t.toLocalDateTime());
                    int mt = rs.getInt("moving_time_sec");
                    if (!rs.wasNull()) w.setMovingTimeSec(mt);
                    int et = rs.getInt("elapsed_time_sec");
                    if (!rs.wasNull()) w.setElapsedTimeSec(et);
                    int hr = rs.getInt("avg_heart_rate");
                    if (!rs.wasNull()) w.setAvgHeartRate(hr);
                    int c = rs.getInt("calories");
                    if (!rs.wasNull()) w.setCalories(c);
                    return w;
                } else {
                    return null;
                }
            }
        }
    }

    public List<Workout> readAllWorkouts() throws SQLException {
        String sql = "SELECT id, user_id, workout_type, date, moving_time_sec, elapsed_time_sec, avg_heart_rate, calories FROM workouts";
        List<Workout> list = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Workout w = new Workout();
                w.setId(rs.getLong("id"));
                w.setUserId(rs.getLong("user_id"));
                w.setWorkoutType(rs.getString("workout_type"));
                Timestamp t = rs.getTimestamp("date");
                if (t != null) w.setDate(t.toLocalDateTime());
                int mt = rs.getInt("moving_time_sec");
                if (!rs.wasNull()) w.setMovingTimeSec(mt);
                int et = rs.getInt("elapsed_time_sec");
                if (!rs.wasNull()) w.setElapsedTimeSec(et);
                int hr = rs.getInt("avg_heart_rate");
                if (!rs.wasNull()) w.setAvgHeartRate(hr);
                int c = rs.getInt("calories");
                if (!rs.wasNull()) w.setCalories(c);
                list.add(w);
            }
        }
        return list;
    }

    public boolean updateWorkout(Workout w) throws SQLException {
        String sql = "UPDATE workouts SET user_id=?, workout_type=?, date=?, moving_time_sec=?, elapsed_time_sec=?, avg_heart_rate=?, calories=? WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, w.getUserId());
            ps.setString(2, w.getWorkoutType());
            ps.setTimestamp(3, Timestamp.valueOf(w.getDate()));
            if (w.getMovingTimeSec() != null) ps.setInt(4, w.getMovingTimeSec()); else ps.setNull(4, Types.INTEGER);
            if (w.getElapsedTimeSec() != null) ps.setInt(5, w.getElapsedTimeSec()); else ps.setNull(5, Types.INTEGER);
            if (w.getAvgHeartRate() != null) ps.setInt(6, w.getAvgHeartRate()); else ps.setNull(6, Types.INTEGER);
            if (w.getCalories() != null) ps.setInt(7, w.getCalories()); else ps.setNull(7, Types.INTEGER);
            ps.setLong(8, w.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteWorkout(long id) throws SQLException {
        String sql = "DELETE FROM workouts WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // ---------------------------
    // Runs CRUD
    // ---------------------------

    public long createRun(Run r) throws SQLException {
        String sql = "INSERT INTO runs (workout_id, distance_miles, avg_pace_sec_per_mile, elevation_gain_feet, steps, avg_cadence_spm) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, r.getWorkoutId());
            if (r.getDistanceMiles() != null) ps.setDouble(2, r.getDistanceMiles()); else ps.setNull(2, Types.DOUBLE);
            if (r.getAvgPaceSecPerMile() != null) ps.setInt(3, r.getAvgPaceSecPerMile()); else ps.setNull(3, Types.INTEGER);
            if (r.getElevationGainFeet() != null) ps.setInt(4, r.getElevationGainFeet()); else ps.setNull(4, Types.INTEGER);
            if (r.getSteps() != null) ps.setInt(5, r.getSteps()); else ps.setNull(5, Types.INTEGER);
            if (r.getAvgCadenceSpm() != null) ps.setInt(6, r.getAvgCadenceSpm()); else ps.setNull(6, Types.INTEGER);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    long id = rs.getLong(1);
                    r.setId(id);
                    return id;
                } else {
                    throw new SQLException("Creating run failed, no ID obtained.");
                }
            }
        }
    }

    public Run readRunById(long id) throws SQLException {
        String sql = "SELECT id, workout_id, distance_miles, avg_pace_sec_per_mile, elevation_gain_feet, steps, avg_cadence_spm FROM runs WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Run r = new Run();
                    r.setId(rs.getLong("id"));
                    r.setWorkoutId(rs.getLong("workout_id"));
                    double d = rs.getDouble("distance_miles");
                    if (!rs.wasNull()) r.setDistanceMiles(d);
                    int pace = rs.getInt("avg_pace_sec_per_mile");
                    if (!rs.wasNull()) r.setAvgPaceSecPerMile(pace);
                    int elev = rs.getInt("elevation_gain_feet");
                    if (!rs.wasNull()) r.setElevationGainFeet(elev);
                    int steps = rs.getInt("steps");
                    if (!rs.wasNull()) r.setSteps(steps);
                    int cad = rs.getInt("avg_cadence_spm");
                    if (!rs.wasNull()) r.setAvgCadenceSpm(cad);
                    return r;
                } else return null;
            }
        }
    }

    public List<Run> readAllRuns() throws SQLException {
        String sql = "SELECT id, workout_id, distance_miles, avg_pace_sec_per_mile, elevation_gain_feet, steps, avg_cadence_spm FROM runs";
        List<Run> out = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Run r = new Run();
                r.setId(rs.getLong("id"));
                r.setWorkoutId(rs.getLong("workout_id"));
                double d = rs.getDouble("distance_miles");
                if (!rs.wasNull()) r.setDistanceMiles(d);
                int pace = rs.getInt("avg_pace_sec_per_mile");
                if (!rs.wasNull()) r.setAvgPaceSecPerMile(pace);
                int elev = rs.getInt("elevation_gain_feet");
                if (!rs.wasNull()) r.setElevationGainFeet(elev);
                int steps = rs.getInt("steps");
                if (!rs.wasNull()) r.setSteps(steps);
                int cad = rs.getInt("avg_cadence_spm");
                if (!rs.wasNull()) r.setAvgCadenceSpm(cad);
                out.add(r);
            }
        }
        return out;
    }

    public boolean updateRun(Run r) throws SQLException {
        String sql = "UPDATE runs SET workout_id=?, distance_miles=?, avg_pace_sec_per_mile=?, elevation_gain_feet=?, steps=?, avg_cadence_spm=? WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, r.getWorkoutId());
            if (r.getDistanceMiles() != null) ps.setDouble(2, r.getDistanceMiles()); else ps.setNull(2, Types.DOUBLE);
            if (r.getAvgPaceSecPerMile() != null) ps.setInt(3, r.getAvgPaceSecPerMile()); else ps.setNull(3, Types.INTEGER);
            if (r.getElevationGainFeet() != null) ps.setInt(4, r.getElevationGainFeet()); else ps.setNull(4, Types.INTEGER);
            if (r.getSteps() != null) ps.setInt(5, r.getSteps()); else ps.setNull(5, Types.INTEGER);
            if (r.getAvgCadenceSpm() != null) ps.setInt(6, r.getAvgCadenceSpm()); else ps.setNull(6, Types.INTEGER);
            ps.setLong(7, r.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteRun(long id) throws SQLException {
        String sql = "DELETE FROM runs WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // ---------------------------
    // Bikes CRUD
    // ---------------------------

    public long createBike(Bike b) throws SQLException {
        String sql = "INSERT INTO bikes (workout_id, distance_miles, avg_speed_mph, max_speed_mph, elevation_gain_feet, avg_pedal_rate_rpm) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, b.getWorkoutId());
            if (b.getDistanceMiles() != null) ps.setDouble(2, b.getDistanceMiles()); else ps.setNull(2, Types.DOUBLE);
            if (b.getAvgSpeedMph() != null) ps.setDouble(3, b.getAvgSpeedMph()); else ps.setNull(3, Types.DOUBLE);
            if (b.getMaxSpeedMph() != null) ps.setDouble(4, b.getMaxSpeedMph()); else ps.setNull(4, Types.DOUBLE);
            if (b.getElevationGainFeet() != null) ps.setInt(5, b.getElevationGainFeet()); else ps.setNull(5, Types.INTEGER);
            if (b.getAvgPedalRateRpm() != null) ps.setInt(6, b.getAvgPedalRateRpm()); else ps.setNull(6, Types.INTEGER);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    long id = rs.getLong(1);
                    b.setId(id);
                    return id;
                } else {
                    throw new SQLException("Creating bike failed, no ID obtained.");
                }
            }
        }
    }

    public Bike readBikeById(long id) throws SQLException {
        String sql = "SELECT id, workout_id, distance_miles, avg_speed_mph, max_speed_mph, elevation_gain_feet, avg_pedal_rate_rpm FROM bikes WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Bike b = new Bike();
                    b.setId(rs.getLong("id"));
                    b.setWorkoutId(rs.getLong("workout_id"));
                    double d = rs.getDouble("distance_miles");
                    if (!rs.wasNull()) b.setDistanceMiles(d);
                    double avg = rs.getDouble("avg_speed_mph");
                    if (!rs.wasNull()) b.setAvgSpeedMph(avg);
                    double max = rs.getDouble("max_speed_mph");
                    if (!rs.wasNull()) b.setMaxSpeedMph(max);
                    int elev = rs.getInt("elevation_gain_feet");
                    if (!rs.wasNull()) b.setElevationGainFeet(elev);
                    int rpm = rs.getInt("avg_pedal_rate_rpm");
                    if (!rs.wasNull()) b.setAvgPedalRateRpm(rpm);
                    return b;
                } else return null;
            }
        }
    }

    public List<Bike> readAllBikes() throws SQLException {
        String sql = "SELECT id, workout_id, distance_miles, avg_speed_mph, max_speed_mph, elevation_gain_feet, avg_pedal_rate_rpm FROM bikes";
        List<Bike> out = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Bike b = new Bike();
                b.setId(rs.getLong("id"));
                b.setWorkoutId(rs.getLong("workout_id"));
                double d = rs.getDouble("distance_miles");
                if (!rs.wasNull()) b.setDistanceMiles(d);
                double avg = rs.getDouble("avg_speed_mph");
                if (!rs.wasNull()) b.setAvgSpeedMph(avg);
                double max = rs.getDouble("max_speed_mph");
                if (!rs.wasNull()) b.setMaxSpeedMph(max);
                int elev = rs.getInt("elevation_gain_feet");
                if (!rs.wasNull()) b.setElevationGainFeet(elev);
                int rpm = rs.getInt("avg_pedal_rate_rpm");
                if (!rs.wasNull()) b.setAvgPedalRateRpm(rpm);
                out.add(b);
            }
        }
        return out;
    }

    public boolean updateBike(Bike b) throws SQLException {
        String sql = "UPDATE bikes SET workout_id=?, distance_miles=?, avg_speed_mph=?, max_speed_mph=?, elevation_gain_feet=?, avg_pedal_rate_rpm=? WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, b.getWorkoutId());
            if (b.getDistanceMiles() != null) ps.setDouble(2, b.getDistanceMiles()); else ps.setNull(2, Types.DOUBLE);
            if (b.getAvgSpeedMph() != null) ps.setDouble(3, b.getAvgSpeedMph()); else ps.setNull(3, Types.DOUBLE);
            if (b.getMaxSpeedMph() != null) ps.setDouble(4, b.getMaxSpeedMph()); else ps.setNull(4, Types.DOUBLE);
            if (b.getElevationGainFeet() != null) ps.setInt(5, b.getElevationGainFeet()); else ps.setNull(5, Types.INTEGER);
            if (b.getAvgPedalRateRpm() != null) ps.setInt(6, b.getAvgPedalRateRpm()); else ps.setNull(6, Types.INTEGER);
            ps.setLong(7, b.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteBike(long id) throws SQLException {
        String sql = "DELETE FROM bikes WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // ---------------------------
    // Swims CRUD
    // ---------------------------

    public long createSwim(Swim s) throws SQLException {
        String sql = "INSERT INTO swims (workout_id, distance_yards, avg_pace_sec_per_100y, avg_stroke_rate_spm) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, s.getWorkoutId());
            if (s.getDistanceYards() != null) ps.setInt(2, s.getDistanceYards()); else ps.setNull(2, Types.INTEGER);
            if (s.getAvgPaceSecPer100Y() != null) ps.setInt(3, s.getAvgPaceSecPer100Y()); else ps.setNull(3, Types.INTEGER);
            if (s.getAvgStrokeRateSpm() != null) ps.setInt(4, s.getAvgStrokeRateSpm()); else ps.setNull(4, Types.INTEGER);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    long id = rs.getLong(1);
                    s.setId(id);
                    return id;
                } else {
                    throw new SQLException("Creating swim failed, no ID obtained.");
                }
            }
        }
    }

    public Swim readSwimById(long id) throws SQLException {
        String sql = "SELECT id, workout_id, distance_yards, avg_pace_sec_per_100y, avg_stroke_rate_spm FROM swims WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Swim s = new Swim();
                    s.setId(rs.getLong("id"));
                    s.setWorkoutId(rs.getLong("workout_id"));
                    int dist = rs.getInt("distance_yards");
                    if (!rs.wasNull()) s.setDistanceYards(dist);
                    int pace = rs.getInt("avg_pace_sec_per_100y");
                    if (!rs.wasNull()) s.setAvgPaceSecPer100Y(pace);
                    int sr = rs.getInt("avg_stroke_rate_spm");
                    if (!rs.wasNull()) s.setAvgStrokeRateSpm(sr);
                    return s;
                } else return null;
            }
        }
    }

    public List<Swim> readAllSwims() throws SQLException {
        String sql = "SELECT id, workout_id, distance_yards, avg_pace_sec_per_100y, avg_stroke_rate_spm FROM swims";
        List<Swim> out = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Swim s = new Swim();
                s.setId(rs.getLong("id"));
                s.setWorkoutId(rs.getLong("workout_id"));
                int dist = rs.getInt("distance_yards");
                if (!rs.wasNull()) s.setDistanceYards(dist);
                int pace = rs.getInt("avg_pace_sec_per_100y");
                if (!rs.wasNull()) s.setAvgPaceSecPer100Y(pace);
                int sr = rs.getInt("avg_stroke_rate_spm");
                if (!rs.wasNull()) s.setAvgStrokeRateSpm(sr);
                out.add(s);
            }
        }
        return out;
    }

    public boolean updateSwim(Swim s) throws SQLException {
        String sql = "UPDATE swims SET workout_id=?, distance_yards=?, avg_pace_sec_per_100y=?, avg_stroke_rate_spm=? WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, s.getWorkoutId());
            if (s.getDistanceYards() != null) ps.setInt(2, s.getDistanceYards()); else ps.setNull(2, Types.INTEGER);
            if (s.getAvgPaceSecPer100Y() != null) ps.setInt(3, s.getAvgPaceSecPer100Y()); else ps.setNull(3, Types.INTEGER);
            if (s.getAvgStrokeRateSpm() != null) ps.setInt(4, s.getAvgStrokeRateSpm()); else ps.setNull(4, Types.INTEGER);
            ps.setLong(5, s.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteSwim(long id) throws SQLException {
        String sql = "DELETE FROM swims WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // ---------------------------
    // Utility: get workouts for a user
    // ---------------------------
    public List<Workout> readWorkoutsByUserId(long userId) throws SQLException {
        String sql = "SELECT id, user_id, workout_type, date, moving_time_sec, elapsed_time_sec, avg_heart_rate, calories FROM workouts WHERE user_id=?";
        List<Workout> out = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Workout w = new Workout();
                    w.setId(rs.getLong("id"));
                    w.setUserId(rs.getLong("user_id"));
                    w.setWorkoutType(rs.getString("workout_type"));
                    Timestamp t = rs.getTimestamp("date");
                    if (t != null) w.setDate(t.toLocalDateTime());
                    int mt = rs.getInt("moving_time_sec");
                    if (!rs.wasNull()) w.setMovingTimeSec(mt);
                    int et = rs.getInt("elapsed_time_sec");
                    if (!rs.wasNull()) w.setElapsedTimeSec(et);
                    int hr = rs.getInt("avg_heart_rate");
                    if (!rs.wasNull()) w.setAvgHeartRate(hr);
                    int c = rs.getInt("calories");
                    if (!rs.wasNull()) w.setCalories(c);
                    out.add(w);
                }
            }
        }
        return out;
    }
}