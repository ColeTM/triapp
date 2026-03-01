package com.example.triapp.controller;

import com.example.triapp.data.DataProvider;
import com.example.triapp.dto.run.*;
import com.example.triapp.mapper.ApiMapper;
import com.example.triapp.model.Run;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/runs")
public class RunController {

    private final DataProvider dataProvider;

    public RunController(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @PostMapping
    public ResponseEntity<RunDto> createRun(@Valid @RequestBody CreateRunRequest req) throws SQLException {
        Run run = ApiMapper.toRun(req);
        long id = dataProvider.createRun(run);
        Run created = dataProvider.readRunById(id);
        return new ResponseEntity<>(ApiMapper.toRunDto(created), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RunDto> getRun(@PathVariable long id) throws SQLException {
        Run r = dataProvider.readRunById(id);
        if (r == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ApiMapper.toRunDto(r));
    }

    @GetMapping
    public ResponseEntity<List<RunDto>> getAllRuns() throws SQLException {
        List<Run> runs = dataProvider.readAllRuns();
        List<RunDto> dtos = runs.stream().map(ApiMapper::toRunDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RunDto> updateRun(@PathVariable long id, @RequestBody UpdateRunRequest req) throws SQLException {
        Run existing = dataProvider.readRunById(id);
        if (existing == null) return ResponseEntity.notFound().build();
        ApiMapper.updateRunFrom(req, existing);
        boolean ok = dataProvider.updateRun(existing);
        if (!ok) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        Run updated = dataProvider.readRunById(id);
        return ResponseEntity.ok(ApiMapper.toRunDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRun(@PathVariable long id) throws SQLException {
        boolean ok = dataProvider.deleteRun(id);
        if (!ok) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }
}