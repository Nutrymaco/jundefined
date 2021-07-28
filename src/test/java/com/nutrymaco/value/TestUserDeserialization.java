package com.nutrymaco.value;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jboss.jandex.Index;
import org.jboss.jandex.Indexer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestUserDeserialization {
    public static class User {
        Value<String> name;
        Value<Integer> age;
        Value<User> parent;

        public User() {
        }

        public Value<String> getName() {
            return name;
        }

        public void setName(Value<String> name) {
            this.name = name;
        }

        public Value<Integer> getAge() {
            return age;
        }

        public void setAge(Value<Integer> age) {
            this.age = age;
        }

        public Value<User> getParent() {
            return parent;
        }

        public void setParent(Value<User> parent) {
            this.parent = parent;
        }
        @Override
        public String toString() {
            return "User{" +
                    "name=" + name +
                    ", age=" + age +
                    ", parent=" + parent +
                    '}';
        }
    }

    ObjectMapper objectMapper = new ObjectMapperFactory(List.of(User.class)).getObjectMapper();

    @Test
    public void testDeserialization() throws JsonProcessingException {
        String userJson = "" +
                "{" +
                "\"name\" : \"Efim\"," +
                "\"parent\" : null" +
                "}";
        var user = objectMapper.readValue(userJson, User.class);
        System.out.println(user);
        assertEquals(user.getAge(), Value.undefined());
        assertEquals(user.getParent(), Value.empty());
        assertTrue(user.getName().isValue());
    }

    @Test
    public void testNestedEntityDeserialization() throws JsonProcessingException {
        String userJson = "{\n" +
                "  \"name\" : \"Efim\",\n" +
                "  \"parent\" : {\n" +
                "    \"age\": 45,\n" +
                "    \"parent\": null\n" +
                "  }\n" +
                "}";
        var user = objectMapper.readValue(userJson, User.class);
        System.out.println(user);
        assertTrue(user.getParent().isValue());
        var parent = user.getParent().get();
        assertTrue(parent.getAge().isValue());
        assertTrue(parent.getParent().isEmpty());
        assertTrue(parent.getName().isUndefined());
    }


    @Test
    public void testSerDerSer() throws JsonProcessingException {
        var user = new User();
        user.setName(Value.value("Efim"));
        user.setAge(Value.undefined());
        user.setParent(Value.empty());

        var userString = objectMapper.writeValueAsString(user);
        System.out.println(userString);
        var parsedUser = this.objectMapper.readValue(userString, User.class);

        assertEquals(user.getName(), parsedUser.getName());
        assertEquals(user.getAge(), parsedUser.getAge());
        assertEquals(user.getParent(), parsedUser.getParent());
    }

    @Test
    public void testNestedObjSerDerSer() throws JsonProcessingException {
        var user = new User();
        user.setName(Value.value("Efim"));
        user.setAge(Value.undefined());
        var parent = new User();
        parent.setParent(Value.undefined());
        parent.setName(Value.empty());
        parent.setAge(Value.value(45));
        user.setParent(Value.value(parent));

        var userString = objectMapper.writeValueAsString(user);
        System.out.println(userString);
        var parsedUser = this.objectMapper.readValue(userString, User.class);

        assertEquals(user.getName(), parsedUser.getName());
        assertEquals(user.getAge(), parsedUser.getAge());
        var parsedParent = user.getParent().get();
        assertEquals(parent.getName(), parsedParent.getName());
        assertEquals(parent.getAge(), parsedParent.getAge());
        assertEquals(parent.getParent(), parsedParent.getParent());
    }

    @Test
    public void testObjectMapperWithJandex() throws IOException {
        Indexer indexer = new Indexer();
        InputStream userClassStream = getClass().getClassLoader()
                .getResourceAsStream("com/nutrymaco/value/TestUser.class");
        InputStream annotationClassStream = getClass().getClassLoader()
                .getResourceAsStream("com/nutrymaco/value/Undefined.class");

        indexer.index(annotationClassStream);
        indexer.index(userClassStream);

        Index index = indexer.complete();

        var objectMapperFactory = new ObjectMapperFactory(index);

        Assertions.assertTrue(objectMapperFactory.targetClasses.contains(TestUser.class));
    }

}
