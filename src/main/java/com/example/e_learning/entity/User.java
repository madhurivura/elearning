package com.example.e_learning.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long userId;

    @Column(name = "full_name")
    private String fullName;

    private String email;
    private String password;
    private String role;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;
}
