package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.AppDocument;

public interface AppDocumentDAO extends JpaRepository<AppDocument, Long>{
    
}
