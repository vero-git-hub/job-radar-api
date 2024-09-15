package org.example.jobradarapi.service;

import org.example.jobradarapi.dto.JobResponse;
import org.example.jobradarapi.model.Job;
import org.example.jobradarapi.repository.JobRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author vero-git-hub
 **/
@Service
public class JobService {

    private final JobRepository jobRepository;
    private final RestTemplate restTemplate;

    @Value("${api.url}")
    private String apiUrl;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
        this.restTemplate = new RestTemplate();
    }

    public void fetchAndSaveJobs(int pageCount) {
        for (int i = 1; i <= pageCount; i++) {
            String url = apiUrl + "?page=" + i;
            JobResponse response = restTemplate.getForObject(url, JobResponse.class);

            if (response != null && response.getJobs() != null) {
                for (Job job : response.getJobs()) {
                    if (!jobRepository.existsByUrl(job.getUrl())) {
                        jobRepository.save(job);
                    }
                }
            }
        }
    }

    public Page<Job> getAllJobs(Pageable pageable) {
        return jobRepository.findAll(pageable);
    }

    public List<Job> getTop10Jobs() {
        return jobRepository.findTop10ByOrderByViewsDesc();
    }

    public Map<String, Long> getLocationStats() {
        return jobRepository.findAll().stream()
                .collect(Collectors.groupingBy(Job::getCandidateRequiredLocation, Collectors.counting()));
    }
}
