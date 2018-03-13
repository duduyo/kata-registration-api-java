package com.orange.registration.adapter;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.junit.MockServerRule;

import static com.orange.registration.ApplicationConfig.EMAIL_API_PATH;
import static com.orange.registration.ApplicationConfig.MOCK_SERVER_PORT;
import static com.orange.registration.Dataset.USER_EMAIL;
import static com.orange.registration.Dataset.USER_LOGIN;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.StringBody.subString;


@RunWith(MockitoJUnitRunner.class)
public class MailApiAdapterTest {

    @Rule
    public MockServerRule mockServerRule = new MockServerRule(this, MOCK_SERVER_PORT);

    private MockServerClient mockServerClient; //= new MockServerClient("127.0.0.1", 8085);

    private final MailApiAdapter mailApiAdapter = new MailApiAdapter();

    @Test
    public void should_call_email_api() {

        // given
        mockServerClient
                .when(
                        request().withMethod("POST").withPath(EMAIL_API_PATH))
                .respond(
                        response().withStatusCode(204));
        // when
        mailApiAdapter.sendConfirmationMail(USER_EMAIL, USER_LOGIN);
        // then
        mockServerClient.verify(request()
                .withMethod("POST")
                .withPath(EMAIL_API_PATH)
                .withBody(subString(USER_EMAIL))
                .withBody(subString(USER_LOGIN)));
    }
}
