package com.example.jobboardapi.model;

/**
 * Represents statistics of jobs by location.
 *
 * <p>This record contains the location and the count of jobs for that location.
 *
 * @param location the name of the location
 * @param count the number of jobs in the specified location
 */
public record JobLocationStatistics(
        String location,
        long count
) {
}
