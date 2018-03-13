package com.orange.registration.service;

import com.orange.registration.adapter.MailApiAdapter;
import com.orange.registration.repository.UserDao;
import com.orange.registration.repository.UserRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.junit.MockServerRule;

import static com.orange.registration.ApplicationConfig.MOCK_SERVER_PORT;
import static com.orange.registration.Dataset.USER_EMAIL;
import static com.orange.registration.Dataset.USER_ID;
import static com.orange.registration.Dataset.USER_LOGIN;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationServiceTest {

    @Rule
    public MockServerRule mockServerRule = new MockServerRule(this, MOCK_SERVER_PORT);

    private MockServerClient mockServerClient; //= new MockServerClient("127.0.0.1", 8085);

    @Mock
    private UserRepository userRepository;

    @Mock
    private MailApiAdapter mailApiAdapter;
    private RegistrationService registrationService;

    @Before
    public void setUp() {
        registrationService = new RegistrationService(userRepository, mailApiAdapter);
    }

    @Test
    public void should_call_storage_and_send_email() {

        // given
        UserDao userDao = new UserDao(USER_ID, USER_LOGIN, USER_EMAIL);

        // when
        registrationService.register(USER_ID, USER_LOGIN, USER_EMAIL);

        verify(userRepository).save(userDao);
        verify(mailApiAdapter).sendConfirmationMail(USER_EMAIL, USER_LOGIN);

    }
}