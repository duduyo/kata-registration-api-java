package com.orange.registration.controller;

import com.orange.registration.repository.UserDao;
import com.orange.registration.repository.UserRepository;
import com.orange.registration.service.RegistrationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static com.orange.registration.ApplicationConfig.USERS_API_PATH;
import static com.orange.registration.Dataset.USER_EMAIL;
import static com.orange.registration.Dataset.USER_ID;
import static com.orange.registration.Dataset.USER_LOGIN;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(RegistrationController.class)
public class RegistrationControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RegistrationService registrationService;

    @MockBean
    private UserRepository userRepository;


    @Test
    public void should_return_CREATED_when_post_user() throws Exception {

        // given
        String user = "{\n" +
                "    \"id\": " + USER_ID + ",\n" +
                "    \"login\": \"" + USER_LOGIN + "\",\n" +
                "    \"email\": \"" + USER_EMAIL + "\"\n" +
                "}";
        // when-then
        mvc.perform(
                post(USERS_API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(user))
           .andExpect(status().isCreated());

        verify(registrationService).register(USER_ID, USER_LOGIN, USER_EMAIL);
    }

    @Test
    public void should_return_OK_when_get_a_user() throws Exception {

        // given
        UserDao userDao = new UserDao(USER_ID, USER_LOGIN, USER_EMAIL);
        when(userRepository.findById(USER_ID)).thenReturn(Optional.ofNullable(userDao));
        // when-then
        mvc.perform(
                get(USERS_API_PATH + "/" + USER_ID).accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("login").value(USER_LOGIN))
           .andExpect(jsonPath("email").value(USER_EMAIL));

        verify(userRepository).findById(USER_ID);

    }
}