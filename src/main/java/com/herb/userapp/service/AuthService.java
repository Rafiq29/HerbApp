package com.herb.userapp.service;

import com.herb.userapp.dto.response.JwtResponse;
import com.herb.userapp.entity.User;
import com.herb.userapp.repo.UserRepo;
import com.herb.userapp.security.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private UserRepo repo;
    private final JwtTokenUtil jwtTokenUtil;

    public AuthService(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found!"));
        return new User(user.getUsername(), user.getPassword(), user.getRole());
    }

    public JwtResponse authenticate(String username, String password) {
        String token = null;
        try {
            Authentication authenticate = manager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            token = jwtTokenUtil.generateToken(authenticate);

        } catch (DisabledException e) {
            logger.error("User Disabled: {}", e.getMessage());
        } catch (BadCredentialsException e) {
            logger.error("Invalid Credentials: {}", e.getMessage());
        }
        return new JwtResponse(token);
    }
}
