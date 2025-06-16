package com.softworkshub.qpoint.security;

import com.softworkshub.qpoint.model.UserEntity;
import com.softworkshub.qpoint.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole() != null ? user.getRole() : "ROLE_USER");

        return new User(
                user.getUsername(),
                user.getPassword(),
                Collections.singleton(authority)
        );
    }
}
