package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.RawData;

public interface RawDataDAO extends JpaRepository<RawData, Long> {
    
}
