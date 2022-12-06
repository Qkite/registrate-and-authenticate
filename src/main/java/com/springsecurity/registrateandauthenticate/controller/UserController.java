package com.springsecurity.registrateandauthenticate.controller;


import com.springsecurity.registrateandauthenticate.domain.dto.UserLoginRequest;
import com.springsecurity.registrateandauthenticate.domain.dto.UserLoginResponse;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest){


        return

    }




}
