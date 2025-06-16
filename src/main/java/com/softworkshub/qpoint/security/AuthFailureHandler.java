package com.softworkshub.qpoint.security;

import com.softworkshub.qpoint.model.UserEntity;
import com.softworkshub.qpoint.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AuthFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String username = request.getParameter("username");
        UserEntity userByUsername = userService.getUserByUsername(username);
        if (userByUsername != null) {
            exception = new LockedException("username and password are invalid");
        }else {
            exception = new UsernameNotFoundException("username not found");
        }
        
    }
}
