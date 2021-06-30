package com.nutrymaco.value;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.ReferenceType;

class ValueDeserializers extends Deserializers.Base {
    @Override
    public JsonDeserializer<?> findReferenceDeserializer(ReferenceType refType,
                                                         DeserializationConfig config,
                                                         BeanDescription beanDesc,
                                                         TypeDeserializer contentTypeDeserializer,
                                                         JsonDeserializer<?> contentDeserializer) throws JsonMappingException {
        return new ValueDeserializer(refType, null, contentTypeDeserializer, contentDeserializer);
    }
}
