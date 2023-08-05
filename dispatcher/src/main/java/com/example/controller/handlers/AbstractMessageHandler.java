package com.example.controller.handlers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class AbstractMessageHandler {

    private RabbitTemplate rabbitTemplate;
    
    public AbstractMessageHandler(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    protected RabbitTemplate getRabbitTemplate() {
        return this.rabbitTemplate;
    }

    public abstract void sendToRabbitMQ(Update update);

    public abstract String getFeedback();

    public abstract String getMessageType();
  
}
