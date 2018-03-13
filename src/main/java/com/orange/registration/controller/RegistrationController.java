package com.orange.registration.controller;


import com.orange.registration.repository.UserDao;
import com.orange.registration.repository.UserRepository;
import com.orange.registration.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class RegistrationController {

    private RegistrationService registrationService;
    private UserRepository userRepository;

    @Autowired
    public RegistrationController(RegistrationService registrationService, UserRepository userRepository) {
        this.registrationService = registrationService;
        this.userRepository = userRepository;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/users")
    @ResponseStatus(HttpStatus.CREATED)
    public void postUser(@RequestBody UserDto user) {

        registrationService.register(user.getId(), user.getLogin(), user.getEmail());

    }

    @RequestMapping(method = RequestMethod.GET, path = "/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUser(@PathVariable long userId) {
        Optional<UserDao> userDao = userRepository.findById(userId);
        return new UserDto(userDao.get().getId(), userDao.get().getLogin(), userDao.get().getEmail());

    }

}