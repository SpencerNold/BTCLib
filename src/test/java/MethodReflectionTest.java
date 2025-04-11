import me.spencernold.transformer.Reflection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import util.TestTargetClass;

public class MethodReflectionTest {

    @Test
    public void callTest() {
        TestTargetClass object = new TestTargetClass();
        int i = (int) Reflection.call(TestTargetClass.class.getName(), object, "method", "()I");
        Assertions.assertEquals(5, i);
    }

    @Test
    public void callParameterTest() {
        TestTargetClass object = new TestTargetClass();
        int i = (int) Reflection.call(TestTargetClass.class.getName(), object, "method", "(I)I", 1);
        Assertions.assertEquals(6, i);
    }

    @Test
    public void repeatedCallTest() {
        TestTargetClass object = new TestTargetClass();

        int i = (int) Reflection.call(TestTargetClass.class.getName(), object, "method", "(I)I", 1);
        Assertions.assertEquals(6, i);

        i = (int) Reflection.call(TestTargetClass.class.getName(), object, "method", "(I)I", 1);
        Assertions.assertEquals(7, i);
    }
}
