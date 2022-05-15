/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jwt.base.repository;

import com.jwt.base.enums.ERole;
import com.jwt.base.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author MH
 */
@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    boolean existsByRolName(ERole name);

     
     Role findByRolName(ERole name);

    
}
