package org.example.jobradarapi.repository;

import org.example.jobradarapi.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @author vero-git-hub
 **/
@Repository
public interface JobRepository extends JpaRepository<Job, Long>{
    boolean existsByUrl(String url);
    List<Job> findTop10ByOrderByViewsDesc();
}
