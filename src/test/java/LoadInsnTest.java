import me.spencernold.transformer.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.ClassTestRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@Transformer(className = "Test")
public class LoadInsnTest {

    private final PrintStream sysout = System.out;
    private ByteArrayOutputStream out;

    @Injector(name = "test()V", target = Target.TAIL)
    public void onTest(Object test, @Local(type = Local.Type.ILOAD, index = 1) int i, Callback callback) {
        System.out.println(i);
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
            ClassTestRunner.run(LoadInsnTest.class);
            Assertions.assertEquals("6\n", out.toString());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            assert (false);
        }
    }
}
