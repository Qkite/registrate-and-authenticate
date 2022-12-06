package com.springsecurity.registrateandauthenticate.controller;

import com.springsecurity.registrateandauthenticate.domain.dto.ReviewRequest;
import com.springsecurity.registrateandauthenticate.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reviews")
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("")
    public String writeReview(@RequestBody ReviewRequest request, Authentication authentication){


        String message = reviewService.writeMessage(authentication.getName());


        return message;

    }
}
