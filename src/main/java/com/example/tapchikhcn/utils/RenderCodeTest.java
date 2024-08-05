package com.example.onlineexam.utils;

public class RenderCodeTest {
    public static String setValue(){
        int stringLength = 4;
        String alphaNumericString = "0123456789";
        StringBuilder sb = new StringBuilder(stringLength);

        for (int i = 0; i < stringLength; i++) {
            int index = (int)(alphaNumericString.length() * Math.random());
            sb.append(alphaNumericString.charAt(index));
        }

        return sb.toString();
    }
}
