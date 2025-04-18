package me.spencernold.transformer;

import org.objectweb.asm.Opcodes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Local {

    Type type();
    int index();

    enum Type {
        ALOAD(Opcodes.ALOAD), ILOAD(Opcodes.ILOAD), LLOAD(Opcodes.LLOAD), FLOAD(Opcodes.FLOAD), DLOAD(Opcodes.DLOAD);

        private final int opcode;

        Type(int opcode) {
            this.opcode = opcode;
        }

        public int getOpcode() {
            return opcode;
        }
    }
}
