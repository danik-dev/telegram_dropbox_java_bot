package com.example.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface ConsumerService {
    void consumeTextMessagesUpdates(Update update);
    void consumeDocMessagesUpdates(Update update);
    void consumePhotoMessagesUpdates(Update update);

}
