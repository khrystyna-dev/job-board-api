package com.example.jobboardapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record JobDto(
        String slug,
        @JsonProperty("company_name")
        String companyName,
        String title,
        String description,
        boolean remote,
        String url,
        List<String> tags,
        @JsonProperty("job_types")
        List<String> jobTypes,
        String location,
        @JsonProperty("created_at")
        long createdAt
) {
}
