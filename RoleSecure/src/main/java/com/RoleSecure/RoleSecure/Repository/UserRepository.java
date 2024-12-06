package com.RoleSecure.RoleSecure.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.RoleSecure.RoleSecure.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String name);

}
