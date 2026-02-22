package com.example.triapp.controller;

import com.example.triapp.data.DataProvider;
import com.example.triapp.dto.swim.*;
import com.example.triapp.mapper.ApiMapper;
import com.example.triapp.model.Swim;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/swims")
public class SwimController {

    private final DataProvider dataProvider;

    public SwimController(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @PostMapping
    public ResponseEntity<SwimDto> createSwim(@Valid @RequestBody CreateSwimRequest req) throws SQLException {
        Swim s = ApiMapper.toSwim(req);
        long id = dataProvider.createSwim(s);
        Swim created = dataProvider.readSwimById(id);
        return new ResponseEntity<>(ApiMapper.toSwimDto(created), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SwimDto> getSwim(@PathVariable long id) throws SQLException {
        Swim s = dataProvider.readSwimById(id);
        if (s == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ApiMapper.toSwimDto(s));
    }

    @GetMapping
    public ResponseEntity<List<SwimDto>> getAllSwims() throws SQLException {
        List<Swim> swims = dataProvider.readAllSwims();
        List<SwimDto> dtos = swims.stream().map(ApiMapper::toSwimDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SwimDto> updateSwim(@PathVariable long id, @RequestBody UpdateSwimRequest req) throws SQLException {
        Swim existing = dataProvider.readSwimById(id);
        if (existing == null) return ResponseEntity.notFound().build();
        ApiMapper.updateSwimFrom(req, existing);
        boolean ok = dataProvider.updateSwim(existing);
        if (!ok) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        Swim updated = dataProvider.readSwimById(id);
        return ResponseEntity.ok(ApiMapper.toSwimDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSwim(@PathVariable long id) throws SQLException {
        boolean ok = dataProvider.deleteSwim(id);
        if (!ok) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }
}