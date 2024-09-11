package com.enigmacamp.warung_makan_bahari_api.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="m_menu")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(columnDefinition = "BIGINT CHECK (price>=0)")
    private Long price;
}
