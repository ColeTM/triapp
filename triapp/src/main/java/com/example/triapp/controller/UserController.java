package com.example.triapp.controller;

import com.example.triapp.dto.user.*;
import com.example.triapp.mapper.ApiMapper;
import com.example.triapp.model.User;
import com.example.triapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) { this.userService = userService; }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody CreateUserRequest req) {
        User u = ApiMapper.toUser(req);
        User created = userService.save(u);
        return new ResponseEntity<>(ApiMapper.toUserDto(created), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable long id) {
        User u = userService.getById(id);
        if (u == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ApiMapper.toUserDto(u));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "heightInches", required = false) Integer heightInches,
            @RequestParam(value = "minHeightInches", required = false) Integer minHeightInches,
            @RequestParam(value = "maxHeightInches", required = false) Integer maxHeightInches,
            @RequestParam(value = "weightPounds", required = false) Double weightPounds,
            @RequestParam(value = "minWeightPounds", required = false) Double minWeightPounds,
            @RequestParam(value = "maxWeightPounds", required = false) Double maxWeightPounds
    ) {
        boolean anyFilter = id != null || firstName != null || lastName != null || email != null ||
                minHeightInches != null || maxHeightInches != null || minWeightPounds != null || maxWeightPounds != null;

        if (!anyFilter) {
            List<UserDto> dtos = userService.getAll().stream().map(ApiMapper::toUserDto).collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        }

        List<UserDto> dtos = userService.findByFilters(
                id, firstName, lastName, email,
                heightInches, minHeightInches, maxHeightInches,
                weightPounds, minWeightPounds, maxWeightPounds
        ).stream().map(ApiMapper::toUserDto).collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable long id, @Valid @RequestBody UpdateUserRequest req) {
        User existing = userService.getById(id);
        if (existing == null) return ResponseEntity.notFound().build();
        ApiMapper.updateUserFrom(req, existing);
        User updated = userService.update(existing);
        return ResponseEntity.ok(ApiMapper.toUserDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        boolean ok = userService.delete(id);
        if (!ok) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }
}