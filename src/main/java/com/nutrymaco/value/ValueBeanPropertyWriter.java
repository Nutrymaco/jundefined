package com.nutrymaco.value;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;

public class ValueBeanPropertyWriter extends BeanPropertyWriter {

    public ValueBeanPropertyWriter(BeanPropertyWriter base) {
        super(base);
    }

    @Override
    public void serializeAsField(Object bean, JsonGenerator gen, SerializerProvider prov) throws Exception {
        if (this.get(bean) != Value.undefined()) {
            super.serializeAsField(bean, gen, prov);
        }
    }
}
