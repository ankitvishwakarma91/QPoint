package com.softworkshub.qpoint.controller;


import com.softworkshub.qpoint.model.LoginDto;
import com.softworkshub.qpoint.model.UserEntity;
import com.softworkshub.qpoint.service.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @ModelAttribute
    public void getUserDetails(Model model,Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            UserEntity userByUsername = userService.getUserByUsername(username);
            model.addAttribute("user", userByUsername);
        }
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginDto loginDto, Model model) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getEmail(),
                            loginDto.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "redirect:/home";
        } catch (Exception e) {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    @GetMapping("/profile")
    public String profile(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity user = userService.getUserByUsername(username);
        model.addAttribute("user", user);
        return "Profile";
    }

    @PostMapping("/sign-up")
    public String signUp(@Valid @ModelAttribute UserEntity user , Model model){
        Boolean existsByUsername = userService.existsByUsername(user.getUsername());
        Boolean existsByEmail = userService.existsByUsername(user.getUsername());

        if (existsByUsername || existsByEmail) {
            model.addAttribute("user", user);
            model.addAttribute("error", "Username or email already exists");
            return "signup";
        }
        userService.signUp(user);
        return "redirect:/auth/login";
    }

    @GetMapping("/signup")
    public String signUpPage(Model model){
        model.addAttribute("user", new UserEntity());
        return "signup";
    }
}
