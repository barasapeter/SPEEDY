package com.barasa.speedy.common.util;

public class PhoneNumberValidatorAndStandardizer {

    public static String standardizePhone(String raw) {
        if (raw == null)
            return null;

        String number = raw.replaceAll("[^0-9]", "");

        if (number.startsWith("0") && number.length() == 10) {
            number = number.substring(1);
        }

        if (number.startsWith("254") && number.length() >= 12) {
            number = number.substring(3);
        }

        if (!number.matches("^(7|1)\\d{8}$")) {
            return null;
        }

        return "254" + number;
    }

    public static boolean isValidPhone(String raw) {
        return standardizePhone(raw) != null;
    }
}
