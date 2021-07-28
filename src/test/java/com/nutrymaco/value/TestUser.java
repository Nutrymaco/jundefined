package com.nutrymaco.value;

@Undefined
public class TestUser {

    Value<String> name;
    Value<Integer> age;
    Value<TestUserDeserialization.User> parent;

    public TestUser() {
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

    public Value<TestUserDeserialization.User> getParent() {
        return parent;
    }

    public void setParent(Value<TestUserDeserialization.User> parent) {
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
