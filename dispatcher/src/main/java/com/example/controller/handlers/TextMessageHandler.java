package com.example.controller.handlers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.example.RabbitQueue.TEXT_MESSAGE_UPDATE;;

public class TextMessageHandler extends AbstractMessageHandler {
    
    public TextMessageHandler(RabbitTemplate rabbitTemplate) {
        super(rabbitTemplate);
    }

    @Override
    public void sendToRabbitMQ(Update update) {
        this.getRabbitTemplate().convertAndSend(TEXT_MESSAGE_UPDATE, update);
    }

    @Override
    public String getFeedback() {
        return "Your text message was received";
    }

    @Override
    public String getMessageType() {
        return "TEXT";
    }
}
