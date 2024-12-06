package com.RoleSecure.RoleSecure.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.RoleSecure.RoleSecure.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByName(String userName);

}
