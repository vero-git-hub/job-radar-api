package org.example.jobradarapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

/**
 * @author vero-git-hub
 **/
@Getter
@Setter
@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "slug")
    private String slug;

    @JsonProperty("title")
    @Column(name = "title")
    private String jobTitle;

    @JsonProperty("company_name")
    @Column(name = "company_name")
    private String companyName;

    @JsonProperty("description")
    @Lob
    @Column(name = "description")
    private String jobDescription;

    @Column(name = "location")
    private String location;

    @Column(name = "remote")
    private boolean remote;

    @Column(name = "url")
    private String url;

    @ElementCollection
    @CollectionTable(name = "job_tags", joinColumns = @JoinColumn(name = "job_id"))
    @Column(name = "tag")
    private List<String> tags;

    @ElementCollection
    @CollectionTable(name = "job_types", joinColumns = @JoinColumn(name = "job_id"))
    @Column(name = "job_type")
    @JsonProperty("job_types")
    private List<String> jobTypes;

    @JsonProperty("created_at")
    @Column(name = "created_at")
    private Instant createdAt;
}
