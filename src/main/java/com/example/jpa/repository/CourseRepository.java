package com.example.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jpa.models.Course;
import com.example.jpa.models.Section;

import org.springframework.data.jpa.repository.Query;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course,Integer>{
    
    @Query("SELECT c.sections FROM Course c WHERE c.id = :courseId")
    List<Section> findSectionsByCourseId(Integer courseId);
}
