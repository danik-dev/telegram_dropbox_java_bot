package com.example.entity;

import org.telegram.telegrambots.meta.api.objects.Update;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class JsonConverter implements AttributeConverter<Update, JsonNode> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public JsonNode convertToDatabaseColumn(Update attribute) {
      
        try {
            String jsonValue = objectMapper.writeValueAsString(attribute);
            return objectMapper.readTree(jsonValue);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to convert Update to String" + e);
        }
    }

    @Override
    public Update convertToEntityAttribute(JsonNode dbData) {
        try {
            String jsonString = objectMapper.writeValueAsString(dbData);
            return objectMapper.readValue(jsonString, Update.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to convert String to Update" + e);
        }
    }
    
}
