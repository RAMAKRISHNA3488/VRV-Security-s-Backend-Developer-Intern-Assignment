package com.RoleSecure.RoleSecure.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.RoleSecure.RoleSecure.models.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

}
