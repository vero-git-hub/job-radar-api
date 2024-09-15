package org.example.jobradarapi.controller;

import org.example.jobradarapi.model.Job;
import org.example.jobradarapi.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author vero-git-hub
 **/
@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public Page<Job> getAllJobs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        return jobService.getAllJobs(pageable);
    }

    @GetMapping("/top10")
    public List<Job> getTop10LatestJobs() {
        return jobService.getTop10LatestJobs();
    }

    @GetMapping("/stats")
    public Map<String, Long> getLocationStats() {
        return jobService.getLocationStats();
    }
}
