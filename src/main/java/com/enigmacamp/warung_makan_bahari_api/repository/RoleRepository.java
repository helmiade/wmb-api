package com.enigmacamp.warung_makan_bahari_api.repository;

import com.enigmacamp.warung_makan_bahari_api.constant.ERole;
import com.enigmacamp.warung_makan_bahari_api.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
