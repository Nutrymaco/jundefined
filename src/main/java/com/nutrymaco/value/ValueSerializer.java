package com.nutrymaco.value;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.ReferenceTypeSerializer;
import com.fasterxml.jackson.databind.type.ReferenceType;
import com.fasterxml.jackson.databind.util.NameTransformer;

class ValueSerializer extends ReferenceTypeSerializer<Value<?>> {

    protected ValueSerializer(ReferenceTypeSerializer<?> base, BeanProperty property, TypeSerializer vts, JsonSerializer<?> valueSer, NameTransformer unwrapper, Object suppressableValue, boolean suppressNulls) {
        super(base, property, vts, valueSer, unwrapper, suppressableValue, suppressNulls);
    }

    public ValueSerializer(ReferenceType fullType, boolean staticTyping, TypeSerializer vts, JsonSerializer<Object> ser) {
        super(fullType, staticTyping, vts, ser);
    }

    @Override
    protected ReferenceTypeSerializer<Value<?>> withResolved(BeanProperty beanProperty,
                                                             TypeSerializer typeSerializer,
                                                             JsonSerializer<?> jsonSerializer,
                                                             NameTransformer nameTransformer) {
        return new ValueSerializer(
                this,
                beanProperty,
                typeSerializer,
                jsonSerializer,
                nameTransformer,
                _suppressableValue,
                _suppressNulls);
    }

    @Override
    public ReferenceTypeSerializer<Value<?>> withContentInclusion(Object o, boolean b) {
        return new ValueSerializer(
                this,
                _property,
                _valueTypeSerializer,
                _valueSerializer,
                _unwrapper,
                _suppressableValue,
                _suppressNulls);
    }

    @Override
    protected boolean _isValuePresent(Value<?> value) {
        return value.isValue();
    }

    @Override
    protected Object _getReferenced(Value<?> value) {
        if (value.isValue()) {
            return value.get();
        } else {
            return null;
        }
    }

    @Override
    protected Object _getReferencedIfPresent(Value<?> value) {
        return value.isValue() ? value.get() : null;
    }

    @Override
    public boolean isEmpty(SerializerProvider provider, Value<?> value) {
        return value.isUndefined();
    }



}
