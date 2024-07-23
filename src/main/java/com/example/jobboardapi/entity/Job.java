package com.example.jobboardapi.entity;

import jakarta.persistence.*;

import java.util.List;

import lombok.*;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "job_data")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String slug;
    private String companyName;
    private String title;
    @Column(name = "description", length = 25000)
    private String description;
    private boolean remote;
    private String url;
    @ElementCollection
    @CollectionTable(name = "job_tags", joinColumns = @JoinColumn(name = "job_id"))
    @Column(name = "tag")
    private List<String> tags;
    @ElementCollection
    @CollectionTable(name = "job_types", joinColumns = @JoinColumn(name = "job_id"))
    @Column(name = "type")
    private List<String> jobTypes;
    private String location;
    private long createdAt;
    private int views;
}
