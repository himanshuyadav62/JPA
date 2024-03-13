package com.example.jpa.models;


import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sectionId; 
    private String sectionName; 
    private int    sectionOrder; 

    @ManyToOne
    @JoinColumn(name = "courseId")
    private Course course; 

    @OneToMany(mappedBy = "section" ,cascade = CascadeType.ALL)
    private List<Lecture> lectures; 
}
