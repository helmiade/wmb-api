package com.enigmacamp.warung_makan_bahari_api.repository;

import com.enigmacamp.warung_makan_bahari_api.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    @Query("SELECT c FROM Customer c WHERE c.name ILIKE %:param% or c.phoneNumber like %:param% or c.id ilike %:param%")
    List<Customer> findByPhoneNumberIgnoreCase(String param);
    //List<Customer> findByNameIsContainingIgnoreCaseOrPhoneNumberEquals(String name, String phoneNumber);
}
