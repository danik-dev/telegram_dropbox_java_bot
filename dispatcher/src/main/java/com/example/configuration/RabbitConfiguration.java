package com.example.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {
    
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue textMessageQueue() {
        return new Queue("text_message_update");
    }

     @Bean
    public Queue docMessageQueue() {
        return new Queue("doc_message_update");
    }

     @Bean
    public Queue photoMessageQueue() {
        return new Queue("photo_message_update");
    }

    @Bean
    public Queue answerMessageQueue() {
        return new Queue("answer_message");
    }



}
