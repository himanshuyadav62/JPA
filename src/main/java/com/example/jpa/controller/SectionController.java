package com.example.jpa.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.jpa.models.Course;
import com.example.jpa.models.Section;
import com.example.jpa.repository.CourseRepository;
import com.example.jpa.repository.SectionRepository;

@RestController
public class SectionController {
    private SectionRepository sectionRepository; 
    private CourseRepository courseRepository; 

    
    public SectionController(SectionRepository sectionRepository, CourseRepository courseRepository) {
        this.sectionRepository = sectionRepository;
        this.courseRepository = courseRepository;
    }


    @GetMapping("/courses/{courseId}/sections")
    public ResponseEntity<List<Section>> getAllSections(@PathVariable int courseId){
          return new ResponseEntity<>( this.courseRepository.findSectionsByCourseId(courseId) ,HttpStatus.OK); 
    }

    @GetMapping("/courses/{courseId}/sections/{sectionId}")
    public ResponseEntity<Section> getSectionFromCourse(@PathVariable int courseId, @PathVariable int sectionId){
        Optional<Course> course = this.courseRepository.findById(courseId); 
        if(course.isPresent()){
            List<Section> sections = course.get().getSections(); 
            for(var i : sections){
                if(i.getSectionId() == sectionId){
                    return new ResponseEntity<>(i,HttpStatus.FOUND); 
                }
            }
        }
        return new  ResponseEntity<>(new Section(),HttpStatus.NOT_FOUND); 
    }
}
