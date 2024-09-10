package com.enigmacamp.warung_makan_bahari_api.repository;

import com.enigmacamp.warung_makan_bahari_api.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
}
