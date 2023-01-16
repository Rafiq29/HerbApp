package com.herb.userapp.security;

import com.herb.userapp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private AuthService authService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestTokenHeader = request.getHeader("Authorization");

        String jwtToken = hasAuthorizationBearer(requestTokenHeader);
        String username;

        if (jwtToken != null && SecurityContextHolder.getContext().getAuthentication() == null
                && jwtTokenUtil.validateToken(jwtToken)) {
            username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            UserDetails userDetails = authService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        filterChain.doFilter(request, response);
    }

    private String hasAuthorizationBearer(String requestHeader) {
        String token = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            token = requestHeader.substring(7);
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
        }
        return token;
    }
}
