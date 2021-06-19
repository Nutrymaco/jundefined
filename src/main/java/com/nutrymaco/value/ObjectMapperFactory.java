package com.nutrymaco.value;

import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.util.List;

public class ObjectMapperFactory {

    @SuppressWarnings("rawtypes")
    private final List<Class> targetClasses;

    @SuppressWarnings("rawtypes")
    public ObjectMapperFactory(List<Class> targetClasses) {
        this.targetClasses = targetClasses;
    }


    public ObjectMapper getObjectMapper() {
        return getObjectMapper(new ObjectMapper());
    }

    public ObjectMapper getObjectMapper(ObjectMapper existingObjectMapper) {
        setUpObjectMapper(existingObjectMapper);
        return existingObjectMapper;
    }

    private void setUpObjectMapper(ObjectMapper objectMapper) {
        objectMapper.registerModule(new SimpleModule().addDeserializer(Value.class, new ValueDeserializer()));
        targetClasses.forEach(clazz -> objectMapper
                .registerModule(new SimpleModule().addDeserializer(clazz, new MainDeserializer<>(clazz))));
    }
}
