package me.spencernold.transformer.objects;

import me.spencernold.transformer.Target;
import me.spencernold.transformer.struct.Pair;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class TransformableMethodObject {

    private final Class<?> transformerClass;
    private final String methodName;
    private final Method transformerMethod;
    private final Target target;
    private final int opcode, ordinal;

    public TransformableMethodObject(Class<?> transformerClass, String methodName, Method transformerMethod, Target target, int opcode, int ordinal) {
        this.transformerClass = transformerClass;
        this.methodName = methodName;
        this.transformerMethod = transformerMethod;
        this.target = target;
        this.opcode = opcode;
        this.ordinal = ordinal;
    }

    public int getReturnType() {
        if (methodName.endsWith("V"))
            return Opcodes.RETURN;
        else if (methodName.endsWith(";"))
            return Opcodes.ARETURN;
        else if (methodName.endsWith("B") || methodName.endsWith("S") || methodName.endsWith("I") || methodName.endsWith("Z"))
            return Opcodes.IRETURN;
        else if (methodName.endsWith("J"))
            return Opcodes.LRETURN;
        else if (methodName.endsWith("F"))
            return Opcodes.FRETURN;
        else if (methodName.endsWith("D"))
            return Opcodes.DRETURN;
        return -1;
    }

    public Class<?> getTransformerClass() {
        return transformerClass;
    }

    public String getMethodName() {
        return methodName;
    }

    public Method getTransformerMethod() {
        return transformerMethod;
    }

    public Target getTarget() {
        return target;
    }

    public int getOpcode() {
        return opcode;
    }

    public int getOrdinal() {
        return ordinal;
    }
}
