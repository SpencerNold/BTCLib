import me.spencernold.transformer.Reflection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import util.TestTargetClass;

public class InitReflectionTest {

    @Test
    public void initTest() {
        TestTargetClass object = Reflection.init(TestTargetClass.class, "()V");
        Assertions.assertEquals(5, object.field);
    }

    @Test
    public void initParameterTest() {
        TestTargetClass object = Reflection.init(TestTargetClass.class, "(I)V", 12);
        Assertions.assertEquals(12, object.field);
    }

    @Test
    public void repeatedInitTest() {
        TestTargetClass object = Reflection.init(TestTargetClass.class, "()V");
        Assertions.assertEquals(5, object.field);

        object = Reflection.init(TestTargetClass.class, "()V");
        Assertions.assertEquals(5, object.field);

        object = Reflection.init(TestTargetClass.class, "(I)V", 12);
        Assertions.assertEquals(12, object.field);

        object = Reflection.init(TestTargetClass.class, "(I)V", 42);
        Assertions.assertEquals(42, object.field);
    }
}
