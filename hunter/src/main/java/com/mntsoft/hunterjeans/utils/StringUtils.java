package com.mntsoft.hunterjeans.utils;

public class StringUtils {
    public static boolean isEquals(String s1, String s2) {
        if (s1 == null) {
            return s2 == null;
        } else{
            if (s2 == null) {
                return false;
            }else
                return s1.trim().equals(s2.trim());
        }
    }

    public static boolean isEqualsIgnoreCase(String s1, String s2) {
        if (s1 == null) {
            return s2 == null;
        } else{
            if (s2 == null) {
                return false;
            }else
                return s1.trim().equalsIgnoreCase(s2.trim());
        }
    }

    public static boolean isEmpty(String s) {
        if (s == null || isEqualsIgnoreCase("null",s)) {
            return true;
        } else {
            return s.trim().isEmpty();
        }
    }

    public static String concatWithDots(String s, int length) {
        if (isEmpty(s) || length < 0) {
            return "";
        } else {
            if (s.length() > length) {
                StringBuffer sb = new StringBuffer(s);
                sb = sb.delete(length, sb.length() - 1);
                for (int i = 0; i < 3; i++) {
                    sb.replace(length + i, length + i + 1, ".");
                }
                return sb.toString();
            } else {
                return s;
            }
        }
    }
}
