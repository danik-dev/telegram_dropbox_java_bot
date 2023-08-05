package com.example.controller;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.example.RabbitQueue;
import com.example.service.UpdateProducer;
import com.example.utils.MessageUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;


@Log4j
@Service
@RequiredArgsConstructor
//is not used. It was in example, but i refactored it to MessageProcessor and handlers 
public class UpdateController {

    private final MessageUtils messageUtils;
    private final UpdateProducer updateProducer;

    public void processUpdate(Update update) {
        if (update == null) {
            log.error("Received update is null");
            return;
        }

        if (update.getMessage() != null) {
            distributeMessagesByType(update);
        } else {
            log.error("Received unsupported message type " + update);
        }
    }

    private void distributeMessagesByType(Update update) {
        var message = update.getMessage();
        if (message.getText() != null) {
            processTextMessage(update);
        } else if (message.getDocument() != null) {
            processDocMessage(update);
        } else if (message.getPhoto() != null) {
            processPhotoMessage(update);
        } else {
            setUnsupportedMessageTypeView(update);
        }
    }

    private void setView(SendMessage sendMessage) {
        // telegramBot.sendAnswerMessage(sendMessage);
    }

    private void processTextMessage(Update update) {
        updateProducer.produce(RabbitQueue.TEXT_MESSAGE_UPDATE, update);
        setFilesReceivedView(update);
    }

    private void processPhotoMessage(Update update) {
        updateProducer.produce(RabbitQueue.PHOTO_MESSAGE_UPDATE, update);
        setFilesReceivedView(update);
    }

    private void processDocMessage(Update update) {
        updateProducer.produce(RabbitQueue.DOC_MESSAGE_UPDATE, update);
        setFilesReceivedView(update);
    }

    private void setUnsupportedMessageTypeView(Update update) {
        var sendMessage = messageUtils.genereateSendMessageWithText(update, "Неподдерживаемый тип сообщения");
        setView(sendMessage);
    }

    private void setFilesReceivedView(Update update) {

    }


}
