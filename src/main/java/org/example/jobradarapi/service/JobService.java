package org.example.jobradarapi.service;

import org.example.jobradarapi.dto.JobResponse;
import org.example.jobradarapi.model.Job;
import org.example.jobradarapi.repository.JobRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
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

    @Scheduled(fixedRate = 180000)
    public void fetchAndSaveJobsPeriodically() {
        fetchAndSaveJobs(5);
    }

    public void fetchAndSaveJobs(int pageCount) {
        for (int i = 1; i <= pageCount; i++) {
            String url = apiUrl + "?page=" + i;
            JobResponse response = restTemplate.getForObject(url, JobResponse.class);

            if (response != null && response.getData() != null) {
                for (Job job : response.getData()) {
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

    public List<Job> getTop10LatestJobs() {
        return jobRepository.findTop10ByOrderByCreatedAtDesc();
    }

    public Map<String, Long> getLocationStats() {
        List<Object[]> results = jobRepository.countJobsByLocation();
        Map<String, Long> locationStats = new HashMap<>();
        for (Object[] result : results) {
            String location = (String) result[0];
            Long count = (Long) result[1];
            locationStats.put(location, count);
        }
        return locationStats.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new));
    }
}
