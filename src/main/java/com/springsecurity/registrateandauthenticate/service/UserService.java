package com.springsecurity.registrateandauthenticate.service;

import com.springsecurity.registrateandauthenticate.domain.User;
import com.springsecurity.registrateandauthenticate.repository.UserRepository;
import com.springsecurity.registrateandauthenticate.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${jwt.token.secret}")
    private String key;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public String login(String userName, String userPassword){

        User selectedUser = userRepository.findByUserName(userName).orElseThrow(() -> new RuntimeException("아이디를 찾을 수 없습니다."));
        log.info(selectedUser.getUserName(), selectedUser.getPassword());

        if(!bCryptPasswordEncoder.matches(userPassword, selectedUser.getPassword())){
            throw new RuntimeException("패스워드가 일치하지 않습니다.");
        }

        String token = JwtTokenUtil.createToken(userName, key, 1000*60*60*2); // 2시간


        return token;
    }




}
