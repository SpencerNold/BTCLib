import me.spencernold.transformer.Reflection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import util.TestTargetClass;

public class FieldReflectionTest {

    @Test
    public void loadTest() {
        TestTargetClass object = new TestTargetClass();
        Reflection.setValue(TestTargetClass.class.getName(), object, "field", 6);
        Assertions.assertEquals(6, object.field);
    }

    @Test
    public void storeTest() {
        TestTargetClass object = new TestTargetClass();
        int i = (int) Reflection.getValue(TestTargetClass.class.getName(), object, "field");
        Assertions.assertEquals(5, i);
    }

    @Test
    public void repeatedLoadStoreTest() {
        TestTargetClass object = new TestTargetClass();

        int i = (int) Reflection.getValue(TestTargetClass.class.getName(), object, "field");
        Assertions.assertEquals(5, i);

        Reflection.setValue(TestTargetClass.class.getName(), object, "field", i + 1);
        Assertions.assertEquals(6, object.field);

        i = (int) Reflection.getValue(TestTargetClass.class.getName(), object, "field");
        Assertions.assertEquals(6, i);

        Reflection.setValue(TestTargetClass.class.getName(), object, "field", i + 1);
        Assertions.assertEquals(7, object.field);
    }
}
