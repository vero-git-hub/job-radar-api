package org.example.jobradarapi.controller;

import org.example.jobradarapi.model.Job;
import org.example.jobradarapi.service.JobService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;

/**
 * @author vero-git-hub
 **/
@WebMvcTest(JobController.class)
public class JobControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JobService jobService;

    @Test
    public void testGetAllJobs() throws Exception {
        List<Job> jobs = Arrays.asList(new Job(), new Job());
        Page<Job> jobPage = new PageImpl<>(jobs);
        when(jobService.getAllJobs(any(Pageable.class))).thenReturn(jobPage);

        mockMvc.perform(get("/api/jobs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)));
    }

    @Test
    public void testGetTop10LatestJobs() throws Exception {
        List<Job> jobs = Arrays.asList(new Job(), new Job(), new Job());
        when(jobService.getTop10LatestJobs()).thenReturn(jobs);

        mockMvc.perform(get("/api/jobs/top10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void testGetLocationStats() throws Exception {
        Map<String, Long> stats = new HashMap<>();
        stats.put("Berlin", 5L);
        stats.put("London", 3L);
        when(jobService.getLocationStats()).thenReturn(stats);

        mockMvc.perform(get("/api/jobs/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Berlin").value(5))
                .andExpect(jsonPath("$.London").value(3));
    }
}
