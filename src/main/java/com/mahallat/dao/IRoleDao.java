package com.mahallat.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mahallat.entity.Role;

@Repository("roleRepository")
public interface IRoleDao extends JpaRepository<Role, Integer>{
	Role findByName(String role);
}
