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
    int localIndex();

    enum Type {
        ADDRESS(Opcodes.ALOAD), INTEGER(Opcodes.ILOAD), LONG(Opcodes.LLOAD), FLOAT(Opcodes.FLOAD), DOUBLE(Opcodes.DLOAD);

        private final int opcode;

        Type(int opcode) {
            this.opcode = opcode;
        }

        public int getOpcode() {
            return opcode;
        }
    }
}
