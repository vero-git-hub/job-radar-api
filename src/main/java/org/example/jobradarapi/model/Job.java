package org.example.jobradarapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author vero-git-hub
 **/
@Setter
@Getter
@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "job_description", length = 1000)
    private String jobDescription;

    @Column(name = "job_type")
    private String jobType;

    @Column(name = "category")
    private String category;

    @Column(name = "publication_date")
    private LocalDate publicationDate;

    @Column(name = "candidate_required_location")
    private String candidateRequiredLocation;

    @Column(name = "salary")
    private String salary;

    @Column(name = "url")
    private String url;

    @Column(name = "views")
    private int views = 0;

}
