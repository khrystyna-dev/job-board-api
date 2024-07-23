package com.example.jobboardapi.dto;

import java.util.List;

public record JobApiResponseDto(
        List<JobDto> data,
        LinkDto links
) {
}