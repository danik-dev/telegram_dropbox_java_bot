package com.example.service.impl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static com.example.RabbitQueue.ANSWER_MESSAGE;
import com.example.service.ProducerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProducerServiceImpl implements ProducerService {
    
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void producerAnswer(SendMessage sendMessage) {
        rabbitTemplate.convertAndSend(ANSWER_MESSAGE, sendMessage);
    }
    
}
