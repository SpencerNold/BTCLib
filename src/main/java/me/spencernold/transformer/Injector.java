package me.spencernold.transformer;

import me.spencernold.transformer.adapters.GenericMethodNameAdapterImpl;
import me.spencernold.transformer.adapters.MethodNameAdapter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Injector {
    me.spencernold.transformer.Target target();
    String name();

    int opcode() default 0; // NOP
    int ordinal() default 0; // 1 (first instance of opcode)

    Class<? extends MethodNameAdapter> adapter() default GenericMethodNameAdapterImpl.class;
}
