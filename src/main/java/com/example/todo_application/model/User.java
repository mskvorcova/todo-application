package com.example.todo_application.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table (name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String password;
}
