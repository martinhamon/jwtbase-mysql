/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jwt.base.services;

import com.jwt.base.model.Role;
import com.jwt.base.repository.RoleRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author MH
 */
@Service
public class RolService {

    @Autowired
    RoleRepository rolRepository;

    public RolService() {
    }

    public RolService(RoleRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    public Role getByRolName(Role rolModel) {
        return rolRepository.findByRolName(rolModel.getRolName());
    }

    public boolean existsByRolName(Role rolModel) {
        return rolRepository.existsByRolName(rolModel.getRolName());
    }

   
}
