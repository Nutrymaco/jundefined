package com.nutrymaco.value;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.util.Arrays;

import static com.nutrymaco.value.Value.undefined;

class PostMappingProcessor<T> extends JsonDeserializer<T> {

    private final static ObjectMapper objectMapper = JsonMapper.builder()
            .addModule(new ValueModule())
            .build();

    private final Class<T> resultClass;

    public PostMappingProcessor(Class<T> resultClass) {
        this.resultClass = resultClass;
    }

    @Override
    public T deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        var objWithNulls = objectMapper.readValue(jsonParser, resultClass);

        Arrays.stream(objWithNulls.getClass().getDeclaredFields())
                .filter(field -> field.getType().equals(Value.class))
                .filter(field -> {
                    try {
                        return field.get(objWithNulls) == null;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        return false;
                    }
                })
                .forEach(nullField -> {
                    try {
                        nullField.set(objWithNulls, undefined());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });

        return objWithNulls;
    }
}
