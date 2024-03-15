package com.example.jpa.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.jpa.models.Course;
import com.example.jpa.models.Section;
import com.example.jpa.repository.CourseRepository;
import com.example.jpa.repository.SectionRepository;
import org.springframework.web.bind.annotation.PutMapping;


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

    @PostMapping("/courses/{courseId}/sections")
    public ResponseEntity<Section> addSectionstoCourse(@PathVariable int courseId ,@RequestBody Section section){
        Optional<Course> optionalCourse = this.courseRepository.findById(courseId); 
        if(optionalCourse.isPresent()){
            Course course = optionalCourse.get();
            List<Section> sections = course.getSections();
            sections.add(section); // Adding the new section to the existing list of sections
            section.setCourse(course);
            this.courseRepository.save(course);
            return new ResponseEntity<>(section, HttpStatus.OK); 
        }
        return new ResponseEntity<>(new Section(), HttpStatus.NOT_FOUND); 
    }

    @PutMapping("courses/{courseId}/sections/{sectionId}")
    public ResponseEntity<String> updateSectioEntity(@PathVariable int courseId, @PathVariable int sectionId, @RequestBody Section section) {
        Optional<Course> course = this.courseRepository.findById(courseId);
        if (course.isPresent()) {
            Course course1 = course.get();
            List<Section> sections = course1.getSections();
            boolean check = false;
            for (int i = 0; i < sections.size(); i++) {
                if (sections.get(i).getSectionId() == sectionId) {
                    check = true;
                    section.setCourse(course1);
                    sections.set(i, section);
                    break;
                }
            }
            if (!check) {
                return new ResponseEntity<>("section not found", HttpStatus.NOT_FOUND);
            }
            this.courseRepository.save(course1);
            return new ResponseEntity<>("section updated", HttpStatus.OK);
        }
        return new ResponseEntity<>("course not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/courses/{courseId}/sections/{sectionId}")
    public ResponseEntity<String> deleteSection(@PathVariable int courseId, @PathVariable int sectionId){
        Optional<Course> course = this.courseRepository.findById(courseId); 
        if(course.isPresent()){
            Course course1 = course.get(); 
            List<Section> sections = course1.getSections(); 
            
            for(int i = 0; i < sections.size(); i++){
                if(sections.get(i).getSectionId() == sectionId ){
                    sections.get(i).setCourse(null);
                    sections.remove(i); // Remove the section from the list
                    course1.setSections(sections);
                    this.courseRepository.save(course1); // Update the course repository
                    return new ResponseEntity<>("Section deleted successfully",HttpStatus.OK);
                }
            }
            return new ResponseEntity<>("Section not found",HttpStatus.NOT_FOUND); 
        }
        return new ResponseEntity<>("course not found" , HttpStatus.NOT_FOUND); 
    }
}
