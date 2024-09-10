package com.enigmacamp.warung_makan_bahari_api.repository;

import com.enigmacamp.warung_makan_bahari_api.entity.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TableRepository extends JpaRepository<Table, String> {
    Optional<Table> findByTableNameIgnoreCase(String name);
}
