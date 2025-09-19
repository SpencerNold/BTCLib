import me.spencernold.transformer.Callback;
import me.spencernold.transformer.Injector;
import me.spencernold.transformer.Target;
import me.spencernold.transformer.Transformer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.ClassTestRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@Transformer(className = "Test")
public class HeadTargetTest {

    private final PrintStream sysout = System.out;
    private ByteArrayOutputStream out;

    @Injector(name = "test()V", target = Target.HEAD)
    public void onTest(Object test, Callback callback) {
        System.out.println("Test!");
    }

    @BeforeEach
    public void wrap() {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    @AfterEach
    public void restore() {
        System.setOut(sysout);
    }

    @Test
    public void test() {
        try {
            ClassTestRunner.run(HeadTargetTest.class);
            Assertions.assertTrue(out.toString().endsWith("Test!\n"));
        } catch (Exception e) {
            e.printStackTrace(System.err);
            assert (false);
        }
    }
}
