package com.nutrymaco.value;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

import java.util.List;

public class ValueBeanSerializerModifier extends BeanSerializerModifier {

    public ValueBeanSerializerModifier() {}

    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
        for(int i = 0; i < beanProperties.size(); ++i) {
            BeanPropertyWriter writer = beanProperties.get(i);
            JavaType type = writer.getType();

            if (type.isTypeOrSubTypeOf(Value.class)) {
                beanProperties.set(i, new ValueBeanPropertyWriter(writer));
            }

        }

        return beanProperties;
    }
}
