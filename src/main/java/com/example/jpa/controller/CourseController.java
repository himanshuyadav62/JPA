package com.example.jpa.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.jpa.models.Course;
import com.example.jpa.models.Section;
import com.example.jpa.repository.CourseRepository;
import com.example.jpa.repository.SectionRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
@RestController
public class CourseController {
  
    private CourseRepository courseRepository;
    private SectionRepository sectionRepository;  
   
    
    public CourseController(CourseRepository courseRepository, SectionRepository sectionRepository) {
        this.courseRepository = courseRepository;
        this.sectionRepository = sectionRepository;
    }

    @PostMapping("/course")
    public Course creatCourse(@RequestBody Course course){
       
        List<Section> sections = course.getSections(); 
        for(var i : sections){
            i.setCourse(course);
        }
        course.setSections(sections);
       return  this.courseRepository.save(course); 
    }

    @GetMapping("/courses")
    public List<Course> getCourses(){
        return this.courseRepository.findAll(); 
    }

    @GetMapping("/course/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable int id){
        Optional<Course> course = this.courseRepository.findById(id);
        if(course.isPresent()){
            return new ResponseEntity<>(course.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Course(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/course")
    public ResponseEntity<Course> updateCourse(@RequestBody Course course){
        int id = course.getCourseId(); 
        Optional<Course> newCourse = this.courseRepository.findById(id); 
        if(newCourse.isPresent()){
            return new ResponseEntity<>(this.courseRepository.save(course),HttpStatus.OK); 
        }
        return new ResponseEntity<>(course, HttpStatus.NOT_FOUND);  
    }

    @DeleteMapping("/course/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable int id){
        try {
            this.courseRepository.deleteById(id);
            return new ResponseEntity<>("course Deleted Successfully",HttpStatus.OK); 
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(),HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }
}
