package com.src.mn.util.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD) // For field-level annotations
@Retention(RetentionPolicy.RUNTIME) // Make it available at runtime
public @interface Field {
    String name();
    String type();
    boolean isPrimaryKey() default false;
    boolean isUnique() default false;
}
