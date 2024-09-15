package org.example.jobradarapi.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.jobradarapi.model.Job;

import java.util.List;

/**
 * @author vero-git-hub
 **/
@Getter
@Setter
public class JobResponse {
    private List<Job> data;
}
