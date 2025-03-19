import me.spencernold.transformer.*;
import org.junit.jupiter.api.Test;
import util.ByteCodeClassLoader;
import util.InputStreams;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Transformer(className = "Test")
public class LoadInsnTestCase {

    @Injector(name = "test()V", target = Target.TAIL)
    public void onTest(Object test, @Local(type = Local.Type.INTEGER, index = 1) int i, Callback callback) {
        System.out.println(i);
    }

    @Test
    public void test() {
        try {
            InputStream input = LoadInsnTestCase.class.getResourceAsStream("Test.class");
            assertNotNull(input, "Test.class does not seem to exist");
            byte[] bytes = InputStreams.readAllBytes(input);
            input.close();

            ClassAdapter adapter = new ClassAdapter(52);
            adapter.registerTransformerClass(LoadInsnTestCase.class);
            bytes = adapter.transform("Test", bytes);
            ByteCodeClassLoader loader = new ByteCodeClassLoader();
            Class<?> clazz = loader.loadClassFromBytes("Test", bytes);
            clazz.getDeclaredMethod("test").invoke(clazz.newInstance());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            assert (false);
        }
    }
}
