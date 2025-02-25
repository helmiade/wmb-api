package com.enigmacamp.warung_makan_bahari_api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@jakarta.persistence.Table(name = "m_table")
public class Table {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "table_name", nullable = false, unique = true)
    private String tableName;
}
