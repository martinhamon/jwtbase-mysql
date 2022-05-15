/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jwt.base.services;

/**
 *
 * @author MH
 */
import com.jwt.base.enums.ERole;
import com.jwt.base.implementation.UserDetailsImpl;
import com.jwt.base.model.Role;
import com.jwt.base.model.User;
import com.jwt.base.repository.UserRepository;
import com.jwt.base.request.RegisterUserRequest;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private Role rolModel;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RolService rolService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return UserDetailsImpl.build(user);
    }

    public User save(RegisterUserRequest user) {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        // newUser.setDescription(user.getDescription());
        // newUser.setActive(false);
        rolModel = new Role();
        rolModel.setRolName(ERole.ROLE_USER);
        Role rol = rolService.getByRolName(rolModel);
        Set<Role> roles = new HashSet<>();

        roles.add(rol);
        newUser.setRoles(roles);

        return userRepository.save(newUser);
    }
}
