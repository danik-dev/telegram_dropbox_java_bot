package com.example.controller.handlers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.example.RabbitQueue.PHOTO_MESSAGE_UPDATE;;

public class PhotoMessageHandler extends AbstractMessageHandler {

    public PhotoMessageHandler(RabbitTemplate rabbitTemplate) {
        super(rabbitTemplate);
    }

    @Override
    public void sendToRabbitMQ(Update update) {
        this.getRabbitTemplate().convertAndSend(PHOTO_MESSAGE_UPDATE, update);
    }

    @Override
    public String getFeedback() {
        return "Your photo was received";
    }

    @Override
    public String getMessageType() {
        return "PHOTO";
    }

}
