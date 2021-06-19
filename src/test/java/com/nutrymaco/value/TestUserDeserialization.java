package com.nutrymaco.value;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

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
    }

    ObjectMapper objectMapper = new ObjectMapperFactory(List.of(User.class)).getObjectMapper();

    @Test
    public void testDeserialization() throws JsonProcessingException {
        String userJson = "" +
                "{" +
                "\"name\" : \"Efim\"," +
                "\"parent\" : null" +
                "}";

        User user = objectMapper.readValue(userJson, User.class);

        assertEquals(user.getAge(), Value.undefined());
        assertEquals(user.getParent(), Value.empty());
        assertTrue(user.getName().isValue());
    }
}
