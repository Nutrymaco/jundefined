package com.nutrymaco.value;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.util.List;

public class ObjectMapperFactory {

    private static ObjectMapper instance;

    @SuppressWarnings("rawtypes")
    private final List<Class> targetClasses;

    @SuppressWarnings("rawtypes")
    public ObjectMapperFactory(List<Class> targetClasses) {
        this.targetClasses = targetClasses;
    }


    public synchronized ObjectMapper getObjectMapper() {
        if (instance != null) {
            return instance;
        }
        var objectMapper = new ObjectMapper();
        setUpObjectMapper(objectMapper);
        instance = objectMapper;
        return objectMapper;
    }

    public void setUpObjectMapper(ObjectMapper objectMapper) {
        objectMapper.registerModule(new ValueModule());
        targetClasses.forEach(clazz -> objectMapper
                .registerModule(new SimpleModule()
                        .addDeserializer(clazz, new PostMappingProcessor<>(clazz))));
    }
}
