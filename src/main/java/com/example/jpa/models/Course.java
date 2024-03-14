package com.example.jpa.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseId; 
    private String courseTitle; 
    @Column(
        length = 100
    )
    private String courseDescription; 

    @ManyToMany
    @JoinTable(
        name = "authorCourses",
        joinColumns = @JoinColumn(name = "courseId"),
        inverseJoinColumns = @JoinColumn(name = "authorId")
    )
    private List<Author> authors;
    
   
   @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
   @JsonManagedReference
    private List<Section> sections; 
    
   
}
