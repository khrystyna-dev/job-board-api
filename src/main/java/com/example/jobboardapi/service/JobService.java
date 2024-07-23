package com.example.jobboardapi.service;

import com.example.jobboardapi.model.JobLocationStatistics;
import com.example.jobboardapi.dto.JobResponseDto;
import com.example.jobboardapi.entity.Job;
import java.util.List;

import com.example.jobboardapi.model.PaginatedJobResponse;

/**
 * Service interface for managing job-related operations.
 *
 * <p>This interface defines methods for saving jobs, retrieving job data with pagination,
 * filtering jobs by slug and creation time, and fetching job statistics, recent jobs, and top-viewed jobs.
 */
public interface JobService {

    /**
     * Saves a list of jobs to the repository.
     *
     * @param jobList the list of {@link Job} objects to save
     */
    void saveAll(List<Job> jobList);

    /**
     * Retrieves a paginated and sorted list of jobs.
     *
     * @param size the number of jobs per page
     * @param page the page number
     * @param sortBy the field to sort by
     * @param sortType the sort direction (ASC or DESC)
     * @return a {@link PaginatedJobResponse} containing job data and pagination details
     */
    PaginatedJobResponse findAll(short size, short page, String sortBy, String sortType);

    /**
     * Finds jobs by slug and creation time.
     *
     * @param job the {@link Job} object containing the slug and creation time
     * @return a list of {@link Job} matching the given slug and creation time
     */
    List<Job> findAllJobsBySlugsAndCreationTime(Job job);

    /**
     * Retrieves job statistics grouped by city.
     *
     * @return a list of {@link JobLocationStatistics} with job counts per location
     */
    List<JobLocationStatistics> getStatisticByCity();

    /**
     * Retrieves a list of recent jobs.
     *
     * @param jobCount the number of recent jobs to retrieve
     * @return a list of {@link JobResponseDto} representing the most recent jobs
     */
    List<JobResponseDto> getRecentJobs(int jobCount);

    /**
     * Retrieves a job by its ID and increments its view count.
     *
     * @param id the ID of the job to retrieve
     * @return a {@link JobResponseDto} representing the job with the specified ID
     */
    JobResponseDto getJobById(Long id);

    /**
     * Retrieves the top-viewed jobs.
     *
     * @param jobCount the number of top-viewed jobs to retrieve
     * @return a list of {@link JobResponseDto} representing the top-viewed jobs
     */
    List<JobResponseDto> getTopViewedJob(int jobCount);
}
