# jundefined

# Usage

```java
import com.nutrymaco.value.Value;
import org.jboss.jandex.IndexView;

@Undefined
class User {
    Value<String> name;
    Value<Integer> age;
    Value<User> parent;
    /*
        getters and setters
    */
}

class Main {
    
    IndexView index;
    
    private final ObjectMapper objectMapper = 
            new ObjectMapperFactory(index).getObjectMapper();
    
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

it can be used via [Quarkus extension](https://github.com/Nutrymaco/jundefined-quarkus-extension)
