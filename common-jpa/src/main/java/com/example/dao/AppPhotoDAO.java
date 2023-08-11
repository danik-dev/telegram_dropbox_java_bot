package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.AppPhoto;

public interface AppPhotoDAO extends JpaRepository<AppPhoto, Long> {}
