package com.example.triapp.service.impl;

import com.example.triapp.data.DataProvider;
import com.example.triapp.model.User;
import com.example.triapp.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;

import java.sql.SQLException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final DataProvider dataProvider;

    public UserServiceImpl(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @Override
    @Transactional
    public User save(User user) {
        try {
            long id = dataProvider.createUser(user);
            return dataProvider.readUserById(id);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create user", e);
        }
    }

    @Override
    @Transactional
    public User update(User user) {
        try {
            boolean ok = dataProvider.updateUser(user);
            if (!ok) throw new RuntimeException("User update returned false");
            return dataProvider.readUserById(user.getId());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update user", e);
        }
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        try {
            return dataProvider.deleteUser(id);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete user", e);
        }
    }

    @Override
    public User getById(long id) {
        try {
            return dataProvider.readUserById(id);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to read user by id", e);
        }
    }

    @Override
    public List<User> getAll() {
        try {
            return dataProvider.readAllUsers();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to read all users", e);
        }
    }

    @Override
    public List<User> findByFilters(
            Long id,
            String firstName,
            String lastName,
            String email,
            Integer heightInches,
            Integer minHeightInches,
            Integer maxHeightInches,
            Double weightPounds,
            Double minWeightPounds,
            Double maxWeightPounds) {
        try {
            List<User> base = dataProvider.readAllUsers();

            return base.stream()
                    .filter(u -> {
                        if (id != null) {
                            Long uid = u.getId();
                            if (uid == null || !id.equals(uid)) return false;
                        }
                        if (firstName != null) {
                            String v = u.getFirstName();
                            if (v == null || !v.equalsIgnoreCase(firstName)) return false;
                        }
                        if (lastName != null) {
                            String v = u.getLastName();
                            if (v == null || !v.equalsIgnoreCase(lastName)) return false;
                        }
                        if (email != null) {
                            String v = u.getEmail();
                            if (v == null || !v.equalsIgnoreCase(email)) return false;
                        }

                        if (heightInches != null) {
                            Integer v = u.getHeightInches();
                            if (v == null || !heightInches.equals(v)) return false;
                        }

                        if (minHeightInches != null) {
                            Integer v = u.getHeightInches();
                            if (v == null || v < minHeightInches) return false;
                        }
                        if (maxHeightInches != null) {
                            Integer v = u.getHeightInches();
                            if (v == null || v > maxHeightInches) return false;
                        }

                        if (weightPounds != null) {
                            Double v = u.getWeightPounds();
                            if (v == null || !weightPounds.equals(v)) return false;
                        }

                        if (minWeightPounds != null) {
                            Double v = u.getWeightPounds();
                            if (v == null || v < minWeightPounds) return false;
                        }
                        if (maxWeightPounds != null) {
                            Double v = u.getWeightPounds();
                            if (v == null || v > maxWeightPounds) return false;
                        }

                        return true;
                    })
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to read filtered users", e);
        }
    }
}