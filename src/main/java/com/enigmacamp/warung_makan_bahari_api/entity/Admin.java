package com.enigmacamp.warung_makan_bahari_api.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "m_admin")
@Builder
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column
    private String name;

    @Column
    private String password;

    @OneToOne
    @JoinColumn(name = "m_user_credential_id")
    private UserCredential userCredential;
}
