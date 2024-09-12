package com.enigmacamp.warung_makan_bahari_api.repository;

import com.enigmacamp.warung_makan_bahari_api.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {
}
