package com.example.meuprojeto.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import static org.junit.Assert.*;

public class UserInsertionTest {

    @Test
    void testUserInsertion() throws Exception {

        String randomUserApiUrl = "https://randomuser.me/api/";
        String randomUserJson = fetchDataFromUrl(randomUserApiUrl);


        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode randomUserData = objectMapper.readTree(randomUserJson);
        String randomUserName = randomUserData.path("results").path(0).path("name").path("first").asText();


        String yourApiUrl = "http://localhost:4567/usuarios";


        String requestBody = "{\"nome\": \"" + randomUserName + "\"}";
        int responseCode = postDataToUrl(yourApiUrl, requestBody);


        assertEquals(201, responseCode);
    }

    private String fetchDataFromUrl(String apiUrl) throws Exception {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            return response.toString();
        }
    }

    private int postDataToUrl(String apiUrl, String data) throws Exception {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);


        connection.getOutputStream().write(data.getBytes("UTF-8"));


        return connection.getResponseCode();
    }
}
