package com.src.mn.util.annotations;

import java.lang.reflect.Field;

public class Validator {

    public static boolean validate(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(NotEmpty.class)) {
                field.setAccessible(true);
                try {
                    Object value = field.get(obj);
                    if (value == null || value.toString().trim().isEmpty()) {
                        System.out.println(field.getAnnotation(NotEmpty.class).message());
                        return false;
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
}
