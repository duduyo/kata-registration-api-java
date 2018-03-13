package com.orange.registration.adapter;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.orange.registration.ApplicationConfig.EMAIL_API_PATH;
import static com.orange.registration.ApplicationConfig.MOCK_SERVER_URL;

@Component
public class MailApiAdapter {
    public void sendConfirmationMail(String email, String login) {

        String body = "{\n" +
                "    \"email\": \"" + email + "\",\n" +
                "    \"object\": \"Subscription OK\",\n" +
                "    \"content\": \"You has been successfully registered with the login " + login + "\"\n" +
                "}";

        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate = restTemplateBuilder.build();
        restTemplate.postForEntity(MOCK_SERVER_URL + EMAIL_API_PATH, body, String.class);
    }
}
