package com.springsecurity.registrateandauthenticate.service;


import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    public String writeMessage(String userName){
        return userName + "님의 리뷰가 등록되었습니다.";
    }
}
