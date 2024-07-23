package com.example.jobboardapi;

import com.example.jobboardapi.entity.Job;
import com.example.jobboardapi.repository.JobRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Random;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class JobControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JobRepository jobRepository;
    private Job job;

    @BeforeEach
    void setUp() {
        job = Job.builder()
                .slug("job-slug")
                .companyName("Test Company")
                .title("Job Title")
                .description("Job Description")
                .remote(true)
                .url("http://test.com")
                .tags(List.of("tag1", "tag2"))
                .jobTypes(List.of("full-time", "part-time"))
                .location("Test Location")
                .createdAt(System.currentTimeMillis())
                .views(9999999)
                .build();

        job = jobRepository.save(job);;
    }

    @Test
    @Transactional
    public void testGetAllJobs_ok() throws Exception {
        mockMvc.perform(get("/jobs")
                        .param("size", "20")
                        .param("page", "1")
                        .param("sortType", "DESC")
                        .param("sortBy", "views"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.items.size()", greaterThan(0)))
                .andExpect(jsonPath("$.items[0].slug", is("job-slug")));
    }

    @Test
    @Transactional
    public void testGetStatisticByCity_ok() throws Exception {
        mockMvc.perform(get("/jobs/city"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(greaterThan(0))));
    }

    @Test
    @Transactional
    public void testGetRecentJob_ok() throws Exception {
        mockMvc.perform(get("/jobs/recent")
                        .param("jobCount", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", greaterThan(0)))
                .andExpect(jsonPath("$[0].slug", is("job-slug")));
    }

    @Test
    public void testGetJobById_ok() throws Exception {
        mockMvc.perform(get("/jobs/{id}", job.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.slug", is("job-slug")))
                .andExpect(jsonPath("$.company_name", is("Test Company")))
                .andExpect(jsonPath("$.title", is("Job Title")));
        jobRepository.deleteById(job.getId());
    }

    @Test
    @Transactional
    public void testGetTopViewedJob_ok() throws Exception {
        mockMvc.perform(get("/jobs/top-viewed")
                        .param("jobCount", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", greaterThan(0)))
                .andExpect(jsonPath("$[0].views", is(job.getViews() + 1)));
    }

    @Test
    public void testGetJobById_notFound() throws Exception {
        Long id = generateNonExistentId();
        mockMvc.perform(get("/jobs/{id}", id))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Couldn't find job by id: " + id)));
    }

    private Long generateNonExistentId() {
        Random random = new Random();
        long id;
        do {
            id = random.nextLong(1000, Integer.MAX_VALUE);
        } while (jobRepository.existsById(id));
        return id;
    }
}
