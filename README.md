# jundefined

# Usage

```java
import com.nutrymaco.value.Value;

class User {
    Value<String> name;
    Value<Integer> age;
    Value<User> parent;
}

class Main {
    
    private final static ObjectMapper objectMapper = 
            new ObjectMapperFactory(List.of(User.class)).getObjectMapper();
    
    public static void main(String[] args) {
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
```
