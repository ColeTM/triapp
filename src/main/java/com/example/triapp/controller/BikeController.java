package com.example.triapp.controller;

import com.example.triapp.data.DataProvider;
import com.example.triapp.dto.bike.*;
import com.example.triapp.mapper.ApiMapper;
import com.example.triapp.model.Bike;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bikes")
public class BikeController {

    private final DataProvider dataProvider;

    public BikeController(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @PostMapping
    public ResponseEntity<BikeDto> createBike(@Valid @RequestBody CreateBikeRequest req) throws SQLException {
        Bike b = ApiMapper.toBike(req);
        long id = dataProvider.createBike(b);
        Bike created = dataProvider.readBikeById(id);
        return new ResponseEntity<>(ApiMapper.toBikeDto(created), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BikeDto> getBike(@PathVariable long id) throws SQLException {
        Bike b = dataProvider.readBikeById(id);
        if (b == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ApiMapper.toBikeDto(b));
    }

    @GetMapping
    public ResponseEntity<List<BikeDto>> getAllBikes() throws SQLException {
        List<Bike> bikes = dataProvider.readAllBikes();
        List<BikeDto> dtos = bikes.stream().map(ApiMapper::toBikeDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BikeDto> updateBike(@PathVariable long id, @RequestBody UpdateBikeRequest req) throws SQLException {
        Bike existing = dataProvider.readBikeById(id);
        if (existing == null) return ResponseEntity.notFound().build();
        ApiMapper.updateBikeFrom(req, existing);
        boolean ok = dataProvider.updateBike(existing);
        if (!ok) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        Bike updated = dataProvider.readBikeById(id);
        return ResponseEntity.ok(ApiMapper.toBikeDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBike(@PathVariable long id) throws SQLException {
        boolean ok = dataProvider.deleteBike(id);
        if (!ok) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }
}