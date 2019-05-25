package com.jonrib.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jonrib.auth.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
}
