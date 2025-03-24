package util;

public class TestTargetClass {

    public int field;

    public TestTargetClass() {
        this(5);
    }

    TestTargetClass(int value) {
        this.field = value;
    }

    private int method() {
        return field;
    }

    private int method(int i) {
        return field += i;
    }
}
