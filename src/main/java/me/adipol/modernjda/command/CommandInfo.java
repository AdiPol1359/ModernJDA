package me.adipol.modernjda.command;

import me.adipol.modernjda.command.cooldown.CoolDownScope;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandInfo {
    String name();
    String description() default "";
    String[] aliases() default {};
    String[] permissions() default {};
    int coolDown() default 0;
    CoolDownScope coolDownScope() default CoolDownScope.COMMAND;
}
