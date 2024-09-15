package org.example.jobradarapi.service;

import org.example.jobradarapi.model.Job;
import org.example.jobradarapi.repository.JobRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JobServiceTest {

    @Mock
    private JobRepository jobRepository;

    @InjectMocks
    private JobService jobService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllJobs_ShouldReturnPagedJobs() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Job> jobs = Arrays.asList(new Job(), new Job());
        Page<Job> jobPage = new PageImpl<>(jobs, pageable, jobs.size());

        when(jobRepository.findAll(pageable)).thenReturn(jobPage);

        Page<Job> result = jobService.getAllJobs(pageable);

        assertEquals(2, result.getContent().size());
        verify(jobRepository, times(1)).findAll(pageable);
    }

    @Test
    void getTop10LatestJobs_ShouldReturnTop10Jobs() {
        List<Job> jobs = Arrays.asList(new Job(), new Job(), new Job());

        when(jobRepository.findTop10ByOrderByCreatedAtDesc()).thenReturn(jobs);

        List<Job> result = jobService.getTop10LatestJobs();

        assertEquals(3, result.size());
        verify(jobRepository, times(1)).findTop10ByOrderByCreatedAtDesc();
    }

    @Test
    void getLocationStats_ShouldReturnLocationStats() {
        List<Object[]> stats = new ArrayList<>();
        stats.add(new Object[]{"New York", 5L});
        stats.add(new Object[]{"London", 3L});

        when(jobRepository.countJobsByLocation()).thenReturn(stats);

        Map<String, Long> result = jobService.getLocationStats();

        assertEquals(2, result.size());
        assertEquals(5L, result.get("New York"));
        assertEquals(3L, result.get("London"));
        verify(jobRepository, times(1)).countJobsByLocation();
    }
}
