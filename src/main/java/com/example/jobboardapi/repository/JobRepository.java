package com.example.jobboardapi.repository;

import com.example.jobboardapi.model.JobLocationStatistics;
import com.example.jobboardapi.entity.Job;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository interface for accessing job data.
 *
 * <p>This interface provides methods for querying job information from the database,
 * including custom queries for filtering, statistics, and pagination.
 */
public interface JobRepository extends JpaRepository<Job, Long> {

    /**
     * Finds jobs by slug and creation time.
     *
     * @param slug the slug of the job
     * @param createdAt the creation timestamp of the job
     * @return a list of {@link Job} matching the given slug and creation time
     */
    @Query(value = "SELECT DISTINCT *"
            + " FROM job_data j"
            + " WHERE j.slug = ?1 AND j.created_at = ?2",
            nativeQuery = true)
    List<Job> findAllBySlugsAndCreationTime(String slug, long createdAt);

    /**
     * Retrieves job statistics grouped by location.
     *
     * @return a list of {@link JobLocationStatistics} with job counts per location
     */
    @Query("""
            SELECT new com.example.jobboardapi.model.JobLocationStatistics(j.location, count(j))
            FROM Job j
            GROUP BY j.location
            ORDER BY count(j) ASC
            """)
    List<JobLocationStatistics> getStatisticByCity();

    /**
     * Finds the top jobs based on view count.
     *
     * @param limit the maximum number of top jobs to retrieve
     * @return a list of {@link Job} ordered by views in descending order
     */
    @Query(value = "SELECT * FROM job_data ORDER BY views DESC LIMIT :limit", nativeQuery = true)
    List<Job> findTopJobs(@Param("limit") int limit);

    /**
     * Finds the most recent jobs based on creation date.
     *
     * @param limit the maximum number of recent jobs to retrieve
     * @return a list of {@link Job} ordered by creation date in descending order
     */
    @Query(value = "SELECT * FROM job_data ORDER BY created_at DESC LIMIT :limit", nativeQuery = true)
    List<Job> findTopJobsByCreatedAt(@Param("limit") int limit);
}
