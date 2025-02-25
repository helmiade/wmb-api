package com.enigmacamp.warung_makan_bahari_api.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "m_customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone_number", unique = true, nullable = false)
    private String phoneNumber;

    @Column(name = "is_member")
    private Boolean isMember=false;

    @OneToOne
    @JoinColumn(name = "m_user_credential_id")
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    private UserCredential userCredential;
}
