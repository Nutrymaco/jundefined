package com.nutrymaco.value;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;

import java.io.IOException;


class ValueDeserializer extends JsonDeserializer<Value<?>> implements ContextualDeserializer {

    private JavaType valueType;

    @Override
    public ValueDeserializer createContextual(DeserializationContext deserializationContext, BeanProperty property) throws JsonMappingException {
        JavaType wrapperType = property.getType();
        JavaType valueType = wrapperType.containedType(0);
        ValueDeserializer deserializer = new ValueDeserializer();
        deserializer.valueType = valueType;
        return deserializer;
    }

    @Override
    public Value<?> deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        var node = deserializationContext.readTree(parser);

        if (node.isMissingNode()) {
            return Value.undefined();
        } else {
            var value = deserializationContext.readValue(parser, valueType);
            return Value.value(value);
        }
    }

    @Override
    public Value<?> getNullValue(DeserializationContext ctxt) throws JsonMappingException {
        return Value.empty();
    }

    @Override
    public Object getEmptyValue(DeserializationContext ctxt) throws JsonMappingException {
        return Value.undefined();
    }
}
