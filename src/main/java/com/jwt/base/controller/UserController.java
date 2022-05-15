/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jwt.base.controller;

import com.jwt.base.repository.RoleRepository;
import com.jwt.base.repository.UserRepository;
import com.jwt.base.request.LoginRequest;
import com.jwt.base.request.RegisterUserRequest;
import com.jwt.base.response.Response;
import com.jwt.base.services.JwtService;
import com.jwt.base.services.UserDetailsServiceImpl;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author MH
 */
@RestController
public class UserController {
  
    
    private Response response;
   
    private JwtService jwtService;
    
   
  
   @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public UserController(Response response, JwtService jwtService) {
        this.response = response;
        this.jwtService = jwtService;
        
       
       // this.userDetailsService = userDetailsService;
    }

    
    

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody LoginRequest activeUser) {

       UserDetails user = userDetailsService.loadUserByUsername(activeUser.getUsername());

        List<String> roleList = user.getAuthorities()
                .stream()
                .map(autthority -> autthority.getAuthority()).collect(Collectors.toList());
        String token = jwtService.createToken(user.getUsername(), roleList);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

   
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json")
   
    public ResponseEntity<?> listUsers(@RequestBody  RegisterUserRequest userRequet) {

        return ResponseEntity.ok(userDetailsService.save(userRequet));
        
        
      /* response.setMessage("Acceso admin OK");
        response.setStatus("OK");
        return new ResponseEntity<>(response, HttpStatus.OK);*/
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/usr", method = RequestMethod.GET, produces = "application/json")
   
    public ResponseEntity<?> listUsr() {
        response.setMessage("Acceso user OK");
        response.setStatus("OK");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
