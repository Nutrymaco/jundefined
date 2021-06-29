package com.nutrymaco.value;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.fasterxml.jackson.databind.ser.impl.ReadOnlyClassToSerializerMap;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
}
