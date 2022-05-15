/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jwt.base.filter;


import com.jwt.base.services.JwtService;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import static com.jwt.base.constants.Constant.*;
/**
 *
 * @author MH
 */

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    
    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        //Verifico que el token sea correcto y extraigo los datos
        String authHeader = request.getHeader(AUTHORIZATION);
        if (authHeader != null && jwtService.isBearer(authHeader)) {

            try {
                //Pasamos los roles de string a roles de spring boot string role -> GrantedAuthority role
                List<GrantedAuthority> athorities;
                athorities = jwtService.roles(authHeader)
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());

                //Paso a spring las funcionalidades para que maneje la seguridad
                UsernamePasswordAuthenticationToken authentication
                        = new UsernamePasswordAuthenticationToken(jwtService.user(authHeader), null, athorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception ex) {
                 Logger.getLogger(JwtAuthenticationFilter.class.getName()).log(Level.SEVERE, null, ex);
              // response.setStatus(401);
              //  filterChain.doFilter(request, response);
            }

        }

        //Proceso el proximo paso de la peticion 
        filterChain.doFilter(request, response);

        //Sigo trebajando en caso de que vuelva correcto los proximos pasos en la parte siguiente
    }

}
