package com.example.todo_application.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table (name = "notes")
@Data
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;
    @ManyToOne
    private User user;

    public String getPreview() {
        return content.length() < 100 ? content : content.substring(0, 100) + "...";
    }
}
