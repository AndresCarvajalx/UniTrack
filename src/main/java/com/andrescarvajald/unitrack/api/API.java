package com.andrescarvajald.unitrack.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class API<T> implements IAPI<T> {
    private static final String PATH = "http://localhost:8080/api/";
    private final HttpClient client = HttpClient.newHttpClient();
    private final HttpRequest.Builder request = HttpRequest.newBuilder();
    private final ObjectMapper mapper = new ObjectMapper();

    public String getPath() {
        return PATH;
    }

    @Override
    public List<T> get(String endpoint, TypeReference<List<T>> valueTypeRef) {
        String url = PATH + endpoint;
        HttpRequest req = request.uri(URI.create(url)).header("Content-Type", "application/json").GET().build();
        try {
            HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());
            if (res.statusCode() == 200) {
                return mapper.readValue(res.body(), valueTypeRef);
            } else {
                throw new RuntimeException("Failed to fetch data: " + res.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public T get(String endpoint, Class<T> value) {
        String url = PATH + endpoint;
        HttpRequest req = request.uri(URI.create(url)).header("Content-Type", "application/json").GET().build();
        try {
            HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());
            if (res.statusCode() == 200) {
                return mapper.readValue(res.body(), value);
            } else {
                throw new RuntimeException("Failed to fetch data: " + res.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
