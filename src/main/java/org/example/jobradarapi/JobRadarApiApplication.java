package org.example.jobradarapi;

import org.example.jobradarapi.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JobRadarApiApplication implements CommandLineRunner {

    private final JobService jobService;

    @Autowired
    public JobRadarApiApplication(JobService jobService) {
        this.jobService = jobService;
    }

    public static void main(String[] args) {
        SpringApplication.run(JobRadarApiApplication.class, args);
    }

    @Override
    public void run(String... args) {
        jobService.fetchAndSaveJobs(5);
    }

}
