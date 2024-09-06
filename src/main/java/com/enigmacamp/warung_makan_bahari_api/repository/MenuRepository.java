package com.enigmacamp.warung_makan_bahari_api.repository;

import com.enigmacamp.warung_makan_bahari_api.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, String> {
    //query method-> membuat query berdasarkan nama method
    //mencari semua data gunakan findall
    //select * from m_menu where name=?
    List<Menu> findByPriceBetween(Long lower, Long upper);
    List<Menu> findByNameIsContainingIgnoreCase(String name);
    List<Menu> findByNameIsContainingIgnoreCaseAndPriceBetween(String name, Long priceFrom, Long priceTo);
    //List<Menu> findByNameIsContainingIgnoreCase(String name);


}
