package util;

import me.spencernold.transformer.ClassAdapter;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ClassTestRunner {

    public static void run(Class<?> testClass) throws Exception {
        InputStream input = testClass.getResourceAsStream("Test.class");
        assertNotNull(input, "Test.class does not seem to exist");
        byte[] bytes = InputStreams.readAllBytes(input);
        input.close();

        ClassAdapter adapter = new ClassAdapter(52);
        adapter.registerTransformerClass(testClass);
        bytes = adapter.transform("Test", bytes);
        ByteCodeClassLoader loader = new ByteCodeClassLoader();
        Class<?> clazz = loader.loadClassFromBytes("Test", bytes);
        clazz.getDeclaredMethod("test").invoke(clazz.newInstance());
    }
}
