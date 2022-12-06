package com.springsecurity.registrateandauthenticate.controller;


import com.springsecurity.registrateandauthenticate.domain.dto.UserLoginRequest;
import com.springsecurity.registrateandauthenticate.domain.dto.UserLoginResponse;
import com.springsecurity.registrateandauthenticate.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public UserLoginResponse login(@RequestBody UserLoginRequest userLoginRequest){

        log.info(userLoginRequest.toString());

        String token = userService.login(userLoginRequest.getUserName(), userLoginRequest.getPassword());

        UserLoginResponse userLoginResponse = new UserLoginResponse(token);
        log.info(userLoginResponse.toString());

        return userLoginResponse;

    }


}
