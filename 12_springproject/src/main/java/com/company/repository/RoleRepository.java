package com.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.entities.Role;
import com.company.entities.User;

public interface RoleRepository extends JpaRepository<Role, Long>{
	public Role findByName(String name);
}
