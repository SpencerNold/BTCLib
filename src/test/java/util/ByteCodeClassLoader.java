package util;

public class ByteCodeClassLoader extends ClassLoader {
    public Class<?> loadClassFromBytes(String className, byte[] bytes) {
        return defineClass(className, bytes, 0, bytes.length);
    }
}