package com.example.service;


import com.example.entity.AppDocument;
import com.example.entity.AppPhoto;

public interface FileService {
    AppDocument getDocument(String docId);
    AppPhoto getPhoto(String photoId);
}
