package com.orange.registration.acceptance;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.junit.MockServerRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.orange.registration.ApplicationConfig.EMAIL_API_PATH;
import static com.orange.registration.ApplicationConfig.MOCK_SERVER_PORT;
import static com.orange.registration.ApplicationConfig.USERS_API_PATH;
import static com.orange.registration.Dataset.USER_EMAIL;
import static com.orange.registration.Dataset.USER_ID;
import static com.orange.registration.Dataset.USER_LOGIN;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RegistrationApiAcceptanceTest {

    @Autowired
    private WebTestClient webTestClient;

    @Rule
    public MockServerRule mockServerRule = new MockServerRule(this, MOCK_SERVER_PORT);

    private MockServerClient mockServerClient;


    @Test
    public void should_store_a_user_and_send_an_email() {

        // @formatter:off
        // given
        mockServerClient
                .when(request().withMethod("POST").withPath(EMAIL_API_PATH))
                .respond(response().withStatusCode(204));

        String user = "{\n" +
                "    \"id\": "+ USER_ID + ",\n" +
                "    \"login\": \"" + USER_LOGIN +"\",\n" +
                "    \"email\": \"" + USER_EMAIL + "\"\n" +
                "}";

        // when
        // then
        webTestClient
                .post()
                    .uri(USERS_API_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .syncBody(user)
                .exchange()
                    .expectStatus().isCreated();

        webTestClient
                .get()
                    .uri(USERS_API_PATH +"/" + USER_ID)
                .exchange()
                    .expectStatus().isOk()
                    .expectBody().jsonPath("login").isEqualTo(USER_LOGIN)
                    .jsonPath("email").isEqualTo(USER_EMAIL);

        // @formatter:on

        mockServerClient.verify(request().withMethod("POST").withPath(EMAIL_API_PATH));

    }


}

