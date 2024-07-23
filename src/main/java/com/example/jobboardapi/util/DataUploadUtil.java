package com.example.jobboardapi.util;

import com.example.jobboardapi.dto.JobApiResponseDto;
import com.example.jobboardapi.dto.JobDto;
import com.example.jobboardapi.entity.Job;
import com.example.jobboardapi.mapper.JobMapper;
import com.example.jobboardapi.service.JobService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility component for uploading job data from an external API.
 *
 * <p>This component fetches job data from an external API periodically and saves
 * new job entries to the repository. It uses a scheduled task to perform the data
 * upload at regular intervals defined by a cron expression.
 */
@Component
@RequiredArgsConstructor
public class DataUploadUtil {
    private final JobService service;
    private final HttpClient client;
    @Value("${api.url.arbeitnow}")
    private String arbeitnowUrl;
    @Value("${api.page-count.data-upload}")
    private String pageCount;

    @Scheduled(cron = "${api.cron.expression.data-upload}")
    @PostConstruct
    public void init() {
        JobApiResponseDto dto = client.get(arbeitnowUrl, JobApiResponseDto.class);
        List<JobDto> jobDtoList = new ArrayList<>(dto.data());

        for (int i = 0; i < Integer.parseInt(pageCount); i++) {
            dto = client.get(dto.links().next(), JobApiResponseDto.class);
            jobDtoList.addAll(dto.data());
        }

        List<Job> jobList = jobDtoList.stream()
                .map(JobMapper.INSTANCE::toEntity)
                .filter(job -> service.findAllJobsBySlugsAndCreationTime(job).isEmpty())
                .toList();
        service.saveAll(jobList);
    }
}
