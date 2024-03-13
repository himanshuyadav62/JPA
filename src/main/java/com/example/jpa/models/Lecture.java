package com.example.jpa.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Lecture {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer lectureId; 
    private String lectureName;
    
    @ManyToOne
    @JoinColumn(name = "sectionId")
    private Section section; 

    @OneToOne(mappedBy = "lecture")
    private Resource resource; 
}
