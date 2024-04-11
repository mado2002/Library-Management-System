package com.example.library.management.Services;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserInfoUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("user".equals(username)) {
            return User.builder()
                    .username("user")
                    .password("$2a$12$YnENHn9VLpOJZ7QsJKBED.5rTWf8dtqKAo8F/trZj2.7OOIRAQDz2")
                    .roles("USER")
                    .build();
        } else if ("admin".equals(username)) {
            return User.builder()
                    .username("admin")
                    .password("$2a$12$0tlDv25z0St5Yq5BphT48uUwdai7fraQzxOcKXE07t2IElp5W7fOq")
                    .roles("ADMIN")
                    .build();
    }
        throw new UsernameNotFoundException("User not found");
    }
}
