package com.example.service.impl;

import org.springframework.stereotype.Service;

import com.example.CryptoTool;
import com.example.dao.AppUserDAO;
import com.example.service.UserActivationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserActivationServiceImpl implements UserActivationService{

    private final AppUserDAO appUserDAO;
    private final CryptoTool cryptoTool;
    
    @Override
    public boolean activation(String cryptoUserId) {
        var userId = cryptoTool.idOf(cryptoUserId);
        var optional = appUserDAO.findById(userId);
        if (optional.isPresent()) {
            var user = optional.get();
            user.setIsActive(true);
            appUserDAO.save(user);
            return true;
        }
        return false;
    }
    
}
