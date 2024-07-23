package com.example.jobboardapi.mapper;

import com.example.jobboardapi.dto.JobDto;
import com.example.jobboardapi.dto.JobResponseDto;
import com.example.jobboardapi.entity.Job;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface for converting between {@link Job}, {@link JobDto}, and {@link JobResponseDto}.
 */
@Mapper(componentModel = "spring")
public interface JobMapper {
    JobMapper INSTANCE = Mappers.getMapper(JobMapper.class);

    Job toEntity(JobDto dto);

    JobDto toDto(Job entity);

    JobResponseDto toResponseDto(Job entity);
}
