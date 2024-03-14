package com.example.jpa.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jpa.models.Section;

@Repository
public interface SectionRepository extends JpaRepository<Section,Integer> {

   
 

}
