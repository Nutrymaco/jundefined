package com.nutrymaco.value;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.fasterxml.jackson.databind.type.ReferenceType;

class ValueSerializers extends Serializers.Base
{
    @Override
    public JsonSerializer<?> findReferenceSerializer(SerializationConfig config,
                                                     ReferenceType refType,
                                                     BeanDescription beanDesc,
                                                     TypeSerializer contentTypeSerializer,
                                                     JsonSerializer<Object> contentValueSerializer)
    {
        final Class<?> raw = refType.getRawClass();
        if (Value.class.isAssignableFrom(raw)) {
            boolean staticTyping = (contentTypeSerializer == null)
                    && config.isEnabled(MapperFeature.USE_STATIC_TYPING);
            return new ValueSerializer(refType, staticTyping,
                    contentTypeSerializer, contentValueSerializer);
        }

        // here will be optimized versions of Value class
//        if (OptionalInt.class.isAssignableFrom(raw)) {
//            return OptionalIntSerializer.INSTANCE;
//        }
//        if (OptionalLong.class.isAssignableFrom(raw)) {
//            return OptionalLongSerializer.INSTANCE;
//        }
//        if (OptionalDouble.class.isAssignableFrom(raw)) {
//            return OptionalDoubleSerializer.INSTANCE;
//        }
        return null;
    }
}
