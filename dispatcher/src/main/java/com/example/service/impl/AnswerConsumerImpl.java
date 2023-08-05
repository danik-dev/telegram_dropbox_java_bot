package com.example.service.impl;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static com.example.RabbitQueue.ANSWER_MESSAGE;

import com.example.controller.TelegramBot;
import com.example.service.AnswerConsumer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerConsumerImpl implements AnswerConsumer{
    private final TelegramBot telegramBot;

    @Override
    @RabbitListener(queues = ANSWER_MESSAGE)
    public void consume(SendMessage sendMessage) {
        telegramBot.sendAnswerMessage(sendMessage);
    }
    
}
