package me.spencernold.transformer;

import me.spencernold.transformer.adapters.ClassNameAdapter;
import me.spencernold.transformer.adapters.FieldNameAdapter;
import me.spencernold.transformer.adapters.MethodNameAdapter;
import org.objectweb.asm.Type;

import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;

public class Reflection {

    private static Reflection systemReflectClass = new Reflection(null, null, null);

    private final Map<String, AccessibleObject> reflectionLookupTable = new HashMap<>();

    private final ClassNameAdapter classNameAdapter;
    private final Class<? extends MethodNameAdapter> methodNameAdapter;
    private final Class<? extends FieldNameAdapter> fieldNameAdapter;

    public Reflection(Class<? extends ClassNameAdapter> classNameAdapter, Class<? extends MethodNameAdapter> methodNameAdapter, Class<? extends FieldNameAdapter> fieldNameAdapter) {
        this.classNameAdapter = classNameAdapter == null ? null : init(classNameAdapter, "()V");
        this.methodNameAdapter = methodNameAdapter;
        this.fieldNameAdapter = fieldNameAdapter;
    }

    private String translateClassName(String className) {
        if (classNameAdapter == null)
            return className;
        return classNameAdapter.adapt(className);
    }

    private String translateMethodName(String className, String methodName) {
        if (methodNameAdapter == null)
            return methodName;
        MethodNameAdapter adapter = init(methodNameAdapter, "(Ljava.lang.String;)V", className);
        return adapter.adapt(methodName);
    }

    private String translateFieldName(String className, String fieldName) {
        if (fieldNameAdapter == null)
            return fieldName;
        FieldNameAdapter adapter = init(fieldNameAdapter, "(Ljava.lang.String;)V", className);
        return adapter.adapt(fieldName);
    }

    private AccessibleObject loadObject(String key) {
        return reflectionLookupTable.get(key);
    }

    private void storeObject(String key, AccessibleObject object) {
        reflectionLookupTable.put(key, object);
    }

    @SuppressWarnings("unchecked")
    public static <T> T init(Class<T> clazz, String descriptor, Object... args) throws ReflectionException {
        String targetName = clazz.getName() + descriptor;
        try {
            AccessibleObject accessible = systemReflectClass.loadObject(targetName);
            Constructor<T> constructor;
            if (accessible == null) {
                Class<?>[] params = getParametersFromDescriptor(descriptor);
                constructor = clazz.getDeclaredConstructor(params);
                constructor.setAccessible(true);
                systemReflectClass.storeObject(targetName, constructor);
            } else
                constructor = (Constructor<T>) accessible;
            return constructor.newInstance(args);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new ReflectionException(e);
        }
    }

    public static Object init(String className, String descriptor, Object... args) {
        return init(getClassInstance(className), descriptor, args);
    }

    public static Object getValue(String className, Object object, String fieldName) {
        String targetName = className + "::" + fieldName;
        try {
            Class<?> clazz = Class.forName(systemReflectClass.translateClassName(className));
            AccessibleObject accessible = systemReflectClass.loadObject(targetName);
            Field field;
            if (accessible == null) {
                fieldName = systemReflectClass.translateFieldName(className, fieldName);
                field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                systemReflectClass.storeObject(targetName, field);
            } else
                field = (Field) accessible;
            return field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException | ClassNotFoundException e) {
            throw new ReflectionException(e);
        }
    }

    public static Object getStaticValue(String className, String fieldName) {
        return getValue(className, null, fieldName);
    }

    public static void setValue(String className, Object object, String fieldName, Object value) {
        String targetName = className + "::" + fieldName;
        try {
            Class<?> clazz = Class.forName(systemReflectClass.translateClassName(className));
            AccessibleObject accessible = systemReflectClass.loadObject(targetName);
            Field field;
            if (accessible == null) {
                fieldName = systemReflectClass.translateFieldName(className, fieldName);
                field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                systemReflectClass.storeObject(targetName, field);
            } else
                field = (Field) accessible;
            field.set(object, value);
        } catch (NoSuchFieldException | IllegalAccessException | ClassNotFoundException e) {
            throw new ReflectionException(e);
        }
    }

    public static void setStaticValue(String className, String fieldName, Object value) {
        setValue(className, null, fieldName, value);
    }

    public static Object call(Class<?> clazz, Object object, String methodName, String descriptor, Object... args) {
        String className = clazz.getName();
        String targetName = className + "::" + methodName + descriptor;
        try {
            AccessibleObject accessible = systemReflectClass.loadObject(targetName);
            Method method;
            if (accessible == null) {
                methodName = systemReflectClass.translateMethodName(className, methodName + descriptor);
                Class<?>[] params = getParametersFromDescriptor(descriptor);
                method = clazz.getDeclaredMethod(methodName.replace(descriptor, ""), params);
                method.setAccessible(true);
                systemReflectClass.storeObject(targetName, method);
            } else
                method = (Method) accessible;
            return method.invoke(object, args);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new ReflectionException(e);
        }
    }

    public static Object call(String className, Object object, String methodName, String descriptor, Object... args) {
        return call(getClassInstance(className), object, methodName, descriptor, args);
    }

    public static Object callStatic(Class<?> clazz, String methodName, String descriptor, Object... args) {
        return call(clazz, null, methodName, descriptor, args);
    }

    public static Object callStatic(String className, String methodName, String descriptor, Object... args) {
        return callStatic(getClassInstance(className), methodName, descriptor, args);
    }

    public static void setSystemReflectClass(Reflection reflection) {
        Reflection.systemReflectClass = reflection;
    }

    private static Class<?>[] getParametersFromDescriptor(String descriptor) {
        Type[] types = Type.getArgumentTypes(descriptor);
        Class<?>[] classes = new Class[types.length];
        for (int i = 0; i < types.length; i++) {
            String name = types[i].getClassName();
            if (name.equals(boolean.class.getName()))
                classes[i] = boolean.class;
            else if (name.equals(byte.class.getName()))
                classes[i] = byte.class;
            else if (name.equals(short.class.getName()))
                classes[i] = short.class;
            else if (name.equals(int.class.getName()))
                classes[i] = int.class;
            else if (name.equals(long.class.getName()))
                classes[i] = long.class;
            else if (name.equals(float.class.getName()))
                classes[i] = float.class;
            else if (name.equals(double.class.getName()))
                classes[i] = double.class;
            else {
                try {
                    classes[i] = Class.forName(name);
                } catch (ClassNotFoundException e) {
                    throw new ReflectionException(e);
                }
            }
        }
        return classes;
    }

    private static Class<?> getClassInstance(String className) {
        className = systemReflectClass.translateClassName(className);
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new ReflectionException(e);
        }
    }

    public static class ReflectionException extends RuntimeException {
        public ReflectionException(Throwable throwable) {
            super(throwable);
        }
    }
}
