package com.example.jobboardapi.controller;

import com.example.jobboardapi.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing job-related operations.
 *
 * <p>This controller provides endpoints for retrieving job information, including
 * listing jobs, retrieving job statistics by city, and fetching recent and top-viewed jobs.
 */
@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobController {
    private final JobService service;

    @GetMapping
    public ResponseEntity<Object> getAllJob(@RequestParam(defaultValue = "20") short size,
                                             @RequestParam(defaultValue = "1") short page,
                                             @RequestParam(defaultValue = "ASC") String sortType,
                                             @RequestParam(defaultValue = "views") String sortBy) {
        return ResponseEntity.ok(service.findAll(size, page, sortBy, sortType));
    }

    @GetMapping("/city")
    public ResponseEntity<Object> getStatisticByCity() {
        return ResponseEntity.ok(service.getStatisticByCity());
    }

    @GetMapping("/recent")
    public ResponseEntity<Object> getRecentJob(@RequestParam(defaultValue = "10") int jobCount) {
        return ResponseEntity.ok(service.getRecentJobs(jobCount));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getJobById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getJobById(id));
    }

    @GetMapping("/top-viewed")
    public ResponseEntity<Object> getTopViewedJob(@RequestParam(defaultValue = "10") int jobCount) {
        return ResponseEntity.ok(service.getTopViewedJob(jobCount));
    }
}