package com.jwt.base.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwt.base.filter.JwtAuthenticationEntryPoint;
import com.jwt.base.filter.JwtAuthenticationFilter;
import com.jwt.base.model.Role;
import com.jwt.base.services.UserDetailsServiceImpl;
//import com.jwt.base.services.UserDetailsServiceImpl;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 *
 * @author MH
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
 @Bean
            public JwtAuthenticationFilter jwtAuthenticationFilter() {
                return new JwtAuthenticationFilter();
            }
    

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

   
    
    

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers("/login", "/register").permitAll()
                .anyRequest().authenticated().and()
                .exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
                    Map<String, Object> responseMap = new HashMap<>();
                    ObjectMapper mapper = new ObjectMapper();
                    response.setStatus(401);
                    responseMap.put("error", true);
                    responseMap.put("message", "Unauthorized");
                    response.setHeader("content-type", "application/json");
                    String responseMsg = mapper.writeValueAsString(responseMap);
                    response.getWriter().write(responseMsg);
                }).and()
                //Voy a utilizar autenticacion basica en la cabezera viene Basic usuario:contraseña
                .httpBasic().and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                //Deshabilito las sesiones
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and() //.addFilterAfter(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                ;
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
