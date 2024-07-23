package com.example.jobboardapi.service.impl;

import com.example.jobboardapi.exception.DataProcessingException;
import com.example.jobboardapi.model.JobLocationStatistics;
import com.example.jobboardapi.dto.JobResponseDto;
import com.example.jobboardapi.entity.Job;
import com.example.jobboardapi.mapper.JobMapper;
import com.example.jobboardapi.model.PaginatedJobResponse;
import com.example.jobboardapi.repository.JobRepository;
import com.example.jobboardapi.service.JobService;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link JobService} interface for managing job operations.
 *
 * <p>This service provides methods for saving jobs, retrieving job data with pagination,
 * filtering jobs by slug and creation time, and fetching statistics and top jobs.
 */
@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;

    @Override
    public void saveAll(List<Job> jobList) {
        jobRepository.saveAll(jobList);
    }

    @Override
    public PaginatedJobResponse findAll(short size, short page, String sortBy, String sortType) {
        Page<Job> job = jobRepository.findAll(PageRequest.of(
                page - 1, size,
                Sort.by(Sort.Direction.valueOf(sortType), sortBy)));

        List<JobResponseDto> ordersDto = job.getContent().stream()
                .map(JobMapper.INSTANCE::toResponseDto)
                .toList();

        return PaginatedJobResponse.builder()
                .totalItems(job.getTotalElements())
                .page(page)
                .totalPages(job.getTotalPages())
                .pageSize(size)
                .items(ordersDto)
                .build();
    }

    @Override
    public List<Job> findAllJobsBySlugsAndCreationTime(Job job) {
        return jobRepository.findAllBySlugsAndCreationTime(
                job.getSlug(),
                job.getCreatedAt());
    }

    @Override
    public List<JobLocationStatistics> getStatisticByCity() {
        return jobRepository.getStatisticByCity();
    }

    @Override
    public List<JobResponseDto> getRecentJobs(int jobCount) {
        return jobRepository.findTopJobsByCreatedAt(jobCount).stream()
                .map(JobMapper.INSTANCE::toResponseDto)
                .toList();
    }

    @Override
    public JobResponseDto getJobById(Long id) {
        Job job = jobRepository.findById(id).orElseThrow(
                () -> new DataProcessingException("Couldn't find job by id: " + id));
        int updatedViews = job.getViews() + 1;
        job.setViews(updatedViews);
        return JobMapper.INSTANCE.toResponseDto(jobRepository.save(job));
    }

    @Override
    public List<JobResponseDto> getTopViewedJob(int jobCount) {
        return jobRepository.findTopJobs(jobCount).stream()
                .map(JobMapper.INSTANCE::toResponseDto)
                .toList();
    }
}