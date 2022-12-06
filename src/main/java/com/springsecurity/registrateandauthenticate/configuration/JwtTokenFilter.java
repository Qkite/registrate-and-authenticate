package com.springsecurity.registrateandauthenticate.configuration;

import com.springsecurity.registrateandauthenticate.domain.User;
import com.springsecurity.registrateandauthenticate.service.UserService;
import com.springsecurity.registrateandauthenticate.util.JwtTokenUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.http.HttpHeaders;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;



@RequiredArgsConstructor
@Getter
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

//        log.info(authorizationHeader);

        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            log.info("적절하지 않은 token입니다.");

            return;
        }


        String token;
        try{
            token = authorizationHeader.split(" ")[1];

        }catch (Exception e){
            log.error("token 추출에 실패했습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        if(JwtTokenUtil.isExpired(token, secretKey)){

            filterChain.doFilter(request, response);
            return ;
        }

        String userName = JwtTokenUtil.getUserName(token, secretKey);

        User user = userService.getUserByUserName(userName);



        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), null, List.of(new SimpleGrantedAuthority("USER")));
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken); // 권한 부여
        filterChain.doFilter(request, response);

    }
}
