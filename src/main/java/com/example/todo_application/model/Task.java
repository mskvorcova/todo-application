package com.example.todo_application.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "tasks")
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    private boolean state = false;
    private LocalDate dueDate;
    @ManyToOne
    private User user;

    public Task() {
    }
    public boolean getState() {
        return this.state;
    }
}
