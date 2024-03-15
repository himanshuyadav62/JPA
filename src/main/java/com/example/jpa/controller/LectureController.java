package com.example.jpa.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.jpa.models.Course;
import com.example.jpa.models.Lecture;
import com.example.jpa.models.Section;
import com.example.jpa.repository.CourseRepository;
import com.example.jpa.repository.LectureRepository;
import com.example.jpa.repository.SectionRepository;

import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
public class LectureController {
    private CourseRepository courseRepository; 
    private SectionRepository sectionRepository; 
    private LectureRepository lectureRepository; 




     public LectureController(CourseRepository courseRepository, SectionRepository sectionRepository,
            LectureRepository lectureRepository) {
        this.courseRepository = courseRepository;
        this.sectionRepository = sectionRepository;
        this.lectureRepository = lectureRepository;
    }


    @GetMapping("/courses/{courseId}/sections/{sectionId}/lectures")
     public ResponseEntity<List<Lecture>> getAllLectuResponseEntity(@PathVariable int courseId,@PathVariable int sectionId){
        Optional<Course> courOptional = this.courseRepository.findById(courseId); 
        if(courOptional.isPresent()){
            List<Section> sections = courOptional.get().getSections(); 
            for(var i : sections){
                if(i.getSectionId() == sectionId){
                    return new ResponseEntity<>(i.getLectures(),HttpStatus.OK); 
                }
            }
            
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
     }
    @GetMapping("/courses/{courseId}/sections/{sectionId}/lectures/{lectureId}")
     public ResponseEntity<Lecture> getLecResponseEntity(@PathVariable int courseId,@PathVariable int sectionId, @PathVariable int lectureId){
        Optional<Course> courOptional = this.courseRepository.findById(courseId); 
        if(courOptional.isPresent()){
            List<Section> sections = courOptional.get().getSections(); 
            for(var i : sections){
                if(i.getSectionId() == sectionId){
                     for(var j : i.getLectures()){
                        if(j.getLectureId() == lectureId){
                            return new ResponseEntity<>(j,HttpStatus.FOUND); 
                        }
                     }
                }
            }
            
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
     }

      
      @PostMapping("/courses/{courseId}/sections/{sectionId}/lectures")
      public ResponseEntity<String> addLectuResponseEntity(@PathVariable int courseId,@PathVariable int sectionId,@RequestBody Lecture lecture) {
        Optional<Course> course = courseRepository.findById(courseId);
        if (!course.isPresent()) {
            return new ResponseEntity<>("Course not found", HttpStatus.NOT_FOUND);
        }

        Optional<Section> section = sectionRepository.findById(sectionId);
        if (!section.isPresent()) {
            return new ResponseEntity<>("Section not found", HttpStatus.NOT_FOUND);
        }

        lecture.setSection(section.get());
        this.lectureRepository.save(lecture); 

        return new ResponseEntity<>("Lecture added successfully " , HttpStatus.CREATED);
      }

      @PutMapping("courses/{courseId}/sections/{sectionId}/lectures/{lectureId}")
      public ResponseEntity<String> updateLecture(@PathVariable int courseId, @PathVariable int sectionId,@RequestBody Lecture lecture) {
        Optional<Course> coursOptional = this.courseRepository.findById(courseId); 
        if(!coursOptional.isPresent()){
            return new ResponseEntity<>("course not found",HttpStatus.NOT_FOUND); 
        }
        Optional<Section> secOption = this.sectionRepository.findById(sectionId); 
        if(!secOption.isPresent()){
            return new ResponseEntity<>("section not found",HttpStatus.NOT_FOUND); 
        }
        lecture.setSection(secOption.get());
        this.lectureRepository.save(lecture); 
        return new ResponseEntity<>("lecture updated",HttpStatus.NOT_FOUND); 
      }

      @DeleteMapping("/courses/{courseId}/sections/{sectionId}/lectures/{lectureId}")
      public ResponseEntity<String> deleteLetureById(@PathVariable int courseId, @PathVariable int sectionId,@PathVariable int lectureId) {
        Optional<Course> coursOptional = this.courseRepository.findById(courseId); 
        if(!coursOptional.isPresent()){
            return new ResponseEntity<>("course not found",HttpStatus.NOT_FOUND); 
        }
        Optional<Section> secOption = this.sectionRepository.findById(sectionId); 
        if(!secOption.isPresent()){
            return new ResponseEntity<>("section not found",HttpStatus.NOT_FOUND); 
        }
        this.lectureRepository.deleteById(lectureId);
        return new ResponseEntity<>("lecture deleted",HttpStatus.NOT_FOUND); 
      }
}
