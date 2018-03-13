package com.orange.registration;

public interface ApplicationConfig {
    int MOCK_SERVER_PORT = 8085;
    String MOCK_SERVER_URL = "http://127.0.0.1:" + MOCK_SERVER_PORT;

    String EMAIL_API_PATH = "/email";
    String USERS_API_PATH = "/users";
}
