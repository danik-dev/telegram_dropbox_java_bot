package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.BinaryContent;

public interface BinaryContentDAO extends JpaRepository<BinaryContent, Long> {
    
}
