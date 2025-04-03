package com.src.mn.util;

import java.util.regex.Pattern;

public class ValidationUtil {

    public static boolean validateUsername(String username) {
        return Pattern.matches("^[a-zA-Z0-9]{5,15}$", username);
    }

    public static boolean validatePassword(String password) {
        return password.length() >= 6;
    }
}
