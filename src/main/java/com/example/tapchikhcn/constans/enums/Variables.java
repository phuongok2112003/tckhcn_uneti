package com.example.tapchikhcn.constans.enums;

/**
 * Các hằng số
 * */
public class Variables {
    /*REGEX*/
    public static final String REGEX_EMAIL = "^[a-zA-Z]+[a-zA-Z0-9]*(\\.[a-zA-Z0-9]+)*@{1}[a-zA-Z]+(\\.[a-zA-Z0-9]+)*(\\.[a-zA-Z]{2,})$";
    public static final String REGEX_PHONE = "^0\\d{9}$";

    public static final String SECRET_KEY = "m4Fq0@#";
    public static final long ACCESS_TOKEN_TIME = 60L * 60 * 1000;
    public static final long ACCESS_CODE= 5L * 60 * 1000;
    public static final long REFRESH_TOKEN_TIME = 30L * 24 * 60 * 60 * 1000;
    public static final int MAX_FAILED_ATTEMPTS = 2;
    public static final int LOCK_TIME_DURATION = 3;    // 3 MINUTES
    public static final byte GENDER_TYPE_MIN = 0;
    public static final byte GENDER_TYPE_MAX = 3;
}
