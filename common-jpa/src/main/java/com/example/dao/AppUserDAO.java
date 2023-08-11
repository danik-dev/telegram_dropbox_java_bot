package com.example.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.AppUser;



public interface AppUserDAO extends JpaRepository<AppUser, Long>{
    Optional<AppUser> findByTelegramUserId(Long telegramUserId);
    Optional<AppUser> findById(Long id);
    Optional<AppUser> findByEmail(String email);
    
}
