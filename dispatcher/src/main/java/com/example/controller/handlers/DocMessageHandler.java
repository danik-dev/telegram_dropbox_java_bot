package com.example.controller.handlers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.example.RabbitQueue.DOC_MESSAGE_UPDATE;

public class DocMessageHandler extends AbstractMessageHandler {

    public DocMessageHandler(RabbitTemplate rabbitTemplate) {
        super(rabbitTemplate);
    }

    @Override
    public void sendToRabbitMQ(Update update) {
        this.getRabbitTemplate().convertAndSend(DOC_MESSAGE_UPDATE, update);
    }

    @Override
    public String getFeedback() {
        return "Your document was received";
    }

    @Override
    public String getMessageType() {
        return "DOC";
    }

    

}
