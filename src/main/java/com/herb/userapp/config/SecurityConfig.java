package com.herb.userapp.config;

import com.herb.userapp.security.JwtAuthenticationEntryPoint;
import com.herb.userapp.security.JwtRequestFilter;
import com.herb.userapp.security.encoder.CustomPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private UserDetailsService jwtUserDetailsService;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public CustomPasswordEncoder customPasswordEncoder() {
        return new CustomPasswordEncoder();
    }

    @Autowired
    public void configGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(customPasswordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();

        http.authorizeRequests()
                .antMatchers("/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "swagger-ui/index.html",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/authenticate",
                        "/register").permitAll()
                .and().authorizeRequests().antMatchers("/user/get/{id}").hasAnyRole("ADMIN","MERCHANT","CLIENT")
                .and().authorizeRequests().antMatchers("/user/get/all").hasAnyRole("ADMIN","MERCHANT")
                .and().authorizeRequests().antMatchers("/user/delete/{id}").hasAnyRole("ADMIN")
                .and().authorizeRequests().anyRequest().authenticated()
                .and().exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
