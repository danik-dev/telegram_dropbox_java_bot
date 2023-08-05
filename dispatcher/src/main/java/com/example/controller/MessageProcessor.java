package com.example.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.example.controller.handlers.AbstractMessageHandler;
import com.example.controller.handlers.DocMessageHandler;
import com.example.controller.handlers.PhotoMessageHandler;
import com.example.controller.handlers.TextMessageHandler;
import com.example.utils.MessageUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@RequiredArgsConstructor
@Log4j
public class MessageProcessor {

    private final MessageUtils messageUtils;
    private final RabbitTemplate rabbitTemplate;

    public SendMessage process(Update update) {
        if (update == null) {
            log.error("Received update is null");
            return null;
        }

        if (update.hasMessage()) {
            String feedback;
            AbstractMessageHandler messageHandler = getMessageHandler(update);

            if (messageHandler == null) {
                feedback = "Неподдерживаемый тип сообщения";
            } else {
                messageHandler.sendToRabbitMQ(update);  
                feedback = messageHandler.getFeedback();
                log.debug("Received " + messageHandler.getMessageType() + " message");
            }

            return messageUtils.genereateSendMessageWithText(update, feedback);
        } else {
            log.error("Received unsupported message type " + update);
            return null;
        }   
    }

    private AbstractMessageHandler getMessageHandler(Update update) {
            var message = update.getMessage();
            if (message.hasText()) {
                return new TextMessageHandler(rabbitTemplate);
            } else if (message.hasDocument()) {
                return new DocMessageHandler(rabbitTemplate);
            } else if (message.hasPhoto()) {
                return new PhotoMessageHandler(rabbitTemplate);
            } else {
                return null;
            }
    }

    
    
        
    
}
