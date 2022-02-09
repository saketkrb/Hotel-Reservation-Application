package com.udacity.model;

import java.util.regex.Pattern;

public class regexPattern {

    private static final String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final String digitsRegex = "[0-9]+";

    public static Pattern getEmailPattern() {
        return Pattern.compile(emailRegex);
    }

    public static Pattern getDigitsPattern() {
        return Pattern.compile(digitsRegex);
    }

}
