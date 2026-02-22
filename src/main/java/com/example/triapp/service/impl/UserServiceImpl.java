package com.example.triapp.service.impl;

import com.example.triapp.data.DataProvider;
import com.example.triapp.model.User;
import com.example.triapp.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}