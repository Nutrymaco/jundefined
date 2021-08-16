package com.nutrymaco.value;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.jboss.jandex.ClassInfo;
import org.jboss.jandex.DotName;
import org.jboss.jandex.IndexView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ObjectMapperFactory {

    private static ObjectMapper instance;

    @SuppressWarnings("rawtypes")
    protected final List<Class> targetClasses;

    @SuppressWarnings("rawtypes")
    public ObjectMapperFactory(List<Class> targetClasses) {
        this.targetClasses = targetClasses;
    }

    public ObjectMapperFactory(IndexView index) {
        this.targetClasses = index.getKnownClasses().stream()
                .filter(classInfo -> classInfo.annotations().containsKey(DotName.createSimple("com.nutrymaco.value.Undefined")))
                .map(ClassInfo::name)
                .map(DotName::toString)
                .map(name -> {
                    try {
                        return Class.forName(name);
                    } catch (ClassNotFoundException e) {
                        System.out.printf("not found class %s\n", name);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public synchronized ObjectMapper getObjectMapper() {
        if (instance != null) {
            return instance;
        }
        var objectMapper = new ObjectMapper();
        setUpObjectMapper(objectMapper);
        instance = objectMapper;
        return objectMapper;
    }

    public void setUpObjectMapper(ObjectMapper objectMapper) {
        objectMapper.registerModule(new ValueModule());
        targetClasses.forEach(clazz -> objectMapper
                .registerModule(new SimpleModule()
                        .addDeserializer(clazz, new PostMappingProcessor<>(clazz, targetClasses))));
    }
}
