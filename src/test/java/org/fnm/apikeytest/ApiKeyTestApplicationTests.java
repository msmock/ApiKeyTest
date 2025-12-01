package org.fnm.apikeytest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class ApiKeyTestApplicationTests {

    public static final String URL = "http://localhost:8080/home";
    public static final String API_KEY = "9e1cac58-0dfd-4fdd-ba84-5d29e8b0e204"; // one of the configured keys

    /**
     * all GET requests should pass
     */
    @Test
    void testGet() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response
                = restTemplate.getForEntity("http://localhost:8080/home", String.class);
        Assert.isTrue(response.getStatusCode().value() == 200, "Should return 200");
    }

    /**
     * POST requests without or non-configured key should fail
     */
    @Test
    void testPostNoKey() {
        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.postForEntity(URL, "dummy", String.class);
        } catch (RestClientException e) {
            System.out.println(e.getMessage());
            Assert.isTrue(e.getMessage().startsWith("405"), "Should return 405");
        }
    }

    @Test
    void testPostInvalidKey() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("X-API-KEY", "invalid-key");

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("id", "1");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(URL, request, String.class);
        } catch (RestClientException e) {
            System.out.println(e.getMessage());
            Assert.isTrue(e.getMessage().startsWith("405"), "Should return 405");
        }
    }

    @Test
    void testPostAuthorized() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("X-API-KEY", API_KEY);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("id", "1");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(URL, request, String.class);

        Assert.isTrue(response.getStatusCode().value() == 200, "Should be OK");
    }

}

