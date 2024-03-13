package com.example.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpa.models.Author;


public interface AuthorRepository extends JpaRepository<Author,Integer>{   // Integer -> primart key data type
        
}
