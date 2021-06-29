package com.nutrymaco.value;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.ReferenceType;
import com.fasterxml.jackson.databind.type.TypeBindings;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.type.TypeModifier;

import java.lang.reflect.Type;

class ValueTypeModifier extends TypeModifier {
    @Override
    public JavaType modifyType(JavaType type, Type jdkType, TypeBindings typeBindings, TypeFactory typeFactory) {
        if (!type.isReferenceType() && !type.isContainerType()) {
            Class<?> raw = type.getRawClass();
            if (raw == Value.class) {
                JavaType refType = type.containedTypeOrUnknown(0);
                return ReferenceType.upgradeFrom(type, refType);
            } else {
                return type;
            }
        } else {
            return type;
        }
    }
}
