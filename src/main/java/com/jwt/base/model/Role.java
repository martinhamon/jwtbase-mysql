/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jwt.base.model;

/**
 *
 * @author MH
 */
import com.jwt.base.enums.ERole;
import javax.persistence.*;
@Entity
@Table(name = "roles")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
        
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private ERole rolName;
        
	public Role() {
	}
	public Role(ERole name) {
		this.rolName = name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public ERole getRolName() {
		return rolName;
	}
	public void setRolName(ERole rolName) {
		this.rolName = rolName;
	}
}
