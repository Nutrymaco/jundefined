package com.nutrymaco.value;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.nutrymaco.value.Value.undefined;

class PostMappingProcessor<T> extends JsonDeserializer<T> {

    private final static ObjectMapper objectMapper = JsonMapper.builder()
            .addModule(new ValueModule())
            .build();

    private final Class<T> resultClass;
    private final List<Class> allClasses;

    public PostMappingProcessor(Class<T> resultClass, List<Class> allClasses) {
        this.resultClass = resultClass;
        this.allClasses = allClasses;
    }

    @Override
    public T deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        var objWithNulls = objectMapper.readValue(jsonParser, resultClass);

        replaceNullOnUndefined(objWithNulls);

        return objWithNulls;
    }

    private void replaceNullOnUndefined(Object objWithNulls) {
        for (var field: objWithNulls.getClass().getDeclaredFields()) {
            try {
                if (field.getType().equals(Value.class)) {
                    if (field.get(objWithNulls) == null) {
                        field.set(objWithNulls, undefined());
                    } else {
                        var value = (Value<?>)field.get(objWithNulls);
                        if (!value.isEmpty()) {
                            var nestedObj = value.get();
                            replaceNullOnUndefined(nestedObj);
                        }
                    }
                    // todo optimize
                } else if (allClasses.stream().anyMatch(objWithNulls.getClass()::equals)) {
                    var nestedObj = field.get(objWithNulls);
                    if (nestedObj == null) {
                        return;
                    }
                    replaceNullOnUndefined(nestedObj);
                }
            } catch (IllegalAccessException e) {
                System.out.printf("cannot access to : %s\n%n", field);
            }
        }
    }
}
