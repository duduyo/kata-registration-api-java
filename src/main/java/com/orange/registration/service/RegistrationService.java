package com.orange.registration.service;

import com.orange.registration.adapter.MailApiAdapter;
import com.orange.registration.repository.UserDao;
import com.orange.registration.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final UserRepository userRepository;
    private MailApiAdapter mailApiAdapter;

    @Autowired
    public RegistrationService(UserRepository userRepository, MailApiAdapter mailApiAdapter) {
        this.userRepository = userRepository;
        this.mailApiAdapter = mailApiAdapter;
    }

    public void register(long id, String login, String email) {
        UserDao userDao = new UserDao(id, login, email);
        userRepository.save(userDao);
        mailApiAdapter.sendConfirmationMail(email, login);
    }

}
