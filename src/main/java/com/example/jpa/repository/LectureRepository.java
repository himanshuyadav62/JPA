package com.example.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jpa.models.Lecture;

@Repository
public interface LectureRepository extends JpaRepository<Lecture,Integer> {
    
}
