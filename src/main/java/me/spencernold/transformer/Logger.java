package me.spencernold.transformer;

public abstract class Logger {

    public static Logger instance = new Logger() {
        @Override
        public void print(String s) {
            System.out.println(s);
        }
    };

    public abstract void print(String s);

    public void printf(String s, Object... objects) {
        print(String.format(s, objects));
    }

    public void print(Throwable throwable) {
        print(throwable.getClass().getName() + ": " + throwable.getMessage());
    }
}
