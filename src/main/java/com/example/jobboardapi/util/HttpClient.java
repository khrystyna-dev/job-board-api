package com.example.jobboardapi.util;

import com.example.jobboardapi.exception.CustomHttpClientException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

/**
 * Utility class for making HTTP GET requests.
 */
@Component
@RequiredArgsConstructor
public class HttpClient {
    private final ObjectMapper objectMapper;

    public <T> T get(String url, Class<T> clazz) {
        HttpGet request = new HttpGet(url);
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            InputStream content = client.execute(request).getEntity().getContent();
            return objectMapper.readValue(content, clazz);
        } catch (IOException e) {
            throw new CustomHttpClientException("Couldn't get data by url " + url);
        }
    }
}
