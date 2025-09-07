package com.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.userservice.entity.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long>{
	AppUser findAppUserByUsername(String username);
	AppUser findAppUserByEmail(String email);
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
}
