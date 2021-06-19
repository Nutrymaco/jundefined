package com.nutrymaco.value;

public class Value<T> {

    private static final Value<?> EMPTY = new Value<>(null, State.EMPTY);
    private static final Value<?> UNDEFINED = new Value<>(null, State.UNDEFINED);

    private final T value;
    private final State state;

    private Value(T value, State state) {
        this.value = value;
        this.state = state;
    }

    public static <T> Value<T> value(T value) {
        return new Value<>(value, State.VALUE);
    }

    public static <T> Value<T> empty() {
        return (Value<T>) EMPTY;
    }

    public static <T> Value<T> undefined() {
        return (Value<T>) UNDEFINED;
    }

    public boolean isValue() {
        return state == State.VALUE;
    }

    public boolean isEmpty() {
        return state == State.EMPTY;
    }

    public boolean isUndefined() {
        return state == State.UNDEFINED;
    }

    public T get() {
        if (state == State.UNDEFINED) {
            throw new IllegalStateException("you cant get undefined value");
        }
        return value;
    }

    private enum State {
        VALUE,
        EMPTY,
        UNDEFINED
    }

    @Override
    public String toString() {
        return "Value{" +
                "value=" + value +
                ", state=" + state +
                '}';
    }
}
