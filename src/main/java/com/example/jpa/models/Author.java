package com.example.jpa.models;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data       // getter , setters ,RequiredArgsConstructor, ToString ,EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Author {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer authorId;   //  null ( default
    private String authorFirstName; 
    private String authorLastName; 
    @Column(
        unique = true,
        nullable = false
    )
    private String authorEmail; 
    private int authorAge;
    @Column(
        updatable = false,
        nullable = false
    ) 
    private LocalDateTime createdAt; 
    @Column(
        insertable = false
    )
    private LocalDateTime lastModified; 

    @ManyToMany(mappedBy = "authors")
    private List<Course> courses; 

    
    
}
