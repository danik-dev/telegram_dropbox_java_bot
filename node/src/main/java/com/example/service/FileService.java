package com.example.service;

import org.telegram.telegrambots.meta.api.objects.Message;

import com.example.entity.AppDocument;
import com.example.entity.AppPhoto;
import com.example.service.enums.LinkType;

public interface FileService {
    AppDocument processDoc(Message telegramMessage);
    AppPhoto processPhoto(Message telegramMessage);
    String generateLink(Long docId, LinkType linkType);
}
