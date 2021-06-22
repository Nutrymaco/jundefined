package com.nutrymaco.value;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.std.ReferenceTypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;


class ValueDeserializer extends ReferenceTypeDeserializer<Value<?>> {

    public ValueDeserializer(JavaType fullType, ValueInstantiator vi, TypeDeserializer typeDeser, JsonDeserializer<?> deser) {
        super(fullType, vi, typeDeser, deser);
    }


    @Override
    protected ReferenceTypeDeserializer<Value<?>> withResolved(TypeDeserializer typeDeserializer,
                                                               JsonDeserializer<?> jsonDeserializer) {
        return new ValueDeserializer(_fullType, _valueInstantiator,
                typeDeserializer, jsonDeserializer);
    }

    @Override
    public Value<?> getNullValue(DeserializationContext deserializationContext) throws JsonMappingException {
        return Value.empty();
    }

    @Override
    public Value<?> referenceValue(Object o) {
        return Value.value(o);
    }

    @Override
    public Value<?> updateReference(Value<?> value, Object o) {
        return Value.value(o);
    }

    @Override
    public Object getReferenced(Value<?> value) {
        return value.isValue() ? value.get() : null;
    }
}
