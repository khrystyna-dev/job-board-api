package com.example.jobboardapi.model;

import com.example.jobboardapi.dto.JobResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * Represents a paginated response for job-related data.
 */
@Builder
@Getter
public class PaginatedJobResponse {
    private long totalItems;
    private int page;
    private int totalPages;
    private short pageSize;
    private List<JobResponseDto> items;
}
