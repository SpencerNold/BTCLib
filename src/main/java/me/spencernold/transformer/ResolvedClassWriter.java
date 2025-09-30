package me.spencernold.transformer;

import org.objectweb.asm.ClassWriter;

public class ResolvedClassWriter extends ClassWriter {

    private final ClassLoader resolver;

    public ResolvedClassWriter(ClassLoader resolver, int flags) {
        super(flags);
        this.resolver = resolver;
    }

    @Override
    protected String getCommonSuperClass(String type1, String type2) {
        ClassLoader classLoader = this.getClassLoader();

        System.out.println("types: " + type1 + ", " + type2);

        Class class1;
        try {
            class1 = Class.forName(type1.replace('/', '.'), false, classLoader);
        } catch (ClassNotFoundException var8) {
            ClassNotFoundException e = var8;
            throw new TypeNotPresentException(type1, e);
        }

        Class class2;
        try {
            class2 = Class.forName(type2.replace('/', '.'), false, classLoader);
        } catch (ClassNotFoundException var7) {
            ClassNotFoundException e = var7;
            throw new TypeNotPresentException(type2, e);
        }

        if (class1.isAssignableFrom(class2)) {
            return type1;
        } else if (class2.isAssignableFrom(class1)) {
            return type2;
        } else if (!class1.isInterface() && !class2.isInterface()) {
            do {
                class1 = class1.getSuperclass();
            } while(!class1.isAssignableFrom(class2));

            return class1.getName().replace('.', '/');
        } else {
            return "java/lang/Object";
        }
    }

    @Override
    protected ClassLoader getClassLoader() {
        return resolver;
    }
}
