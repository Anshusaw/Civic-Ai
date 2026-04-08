package com.incapp.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private String provider; // LOCAL / GOOGLE

    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    
}