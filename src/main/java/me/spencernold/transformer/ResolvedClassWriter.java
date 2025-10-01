package me.spencernold.transformer;

import org.objectweb.asm.ClassWriter;

public class ResolvedClassWriter extends ClassWriter {

    private final ClassLoader resolver;

    public ResolvedClassWriter(ClassLoader resolver, int flags) {
        super(flags);
        this.resolver = resolver;
    }

    @Override
    protected ClassLoader getClassLoader() {
        return resolver;
    }
}
