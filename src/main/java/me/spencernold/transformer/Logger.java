package me.spencernold.transformer;

public interface Logger {

    Logger instance = System.out::println;

    void print(String s);

    default void printf(String s, Object... objects) {
        print(String.format(s, objects));
    }

    default void print(Throwable throwable) {
        print(throwable.getClass().getName() + ": " + throwable.getMessage());
    }
}
