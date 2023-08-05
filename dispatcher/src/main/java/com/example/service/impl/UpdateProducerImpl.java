package com.example.service.impl;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.example.service.UpdateProducer;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class UpdateProducerImpl implements UpdateProducer{

    @Override
    public void produce(String rabbitQueue, Update update) {
        log.debug(update.getMessage().getText());
    }
    
}
