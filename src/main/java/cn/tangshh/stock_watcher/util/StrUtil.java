package cn.tangshh.stock_watcher.util;

/**
 * 字符串工具类
 *
 * @author Tang
 * @version v1.0
 */

public final class StrUtil {
    private StrUtil() {}

    public static boolean isEmpty(String str) {
        if (str == null) return true;
        return str.isEmpty();
    }

    public static boolean nonEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isBlank(String str) {
        if (str == null) return true;
        return str.trim().isEmpty();
    }

    public static boolean nonBlank(String str) {
        return !isBlank(str);
    }

    public static String trim(String str) {
        if (str == null) return null;
        return str.trim();
    }

    public static boolean equals(String str1, String str2) {
        if (str1 == null) {
            return str2 == null;
        }
        if (str2 != null) {
            return str1.equals(str2);
        }
        return false;
    }

    public static boolean equalsIgnoreCase(String str1, String str2) {
        if (str1 == null) {
            return str2 == null;
        }
        if (str2 != null) {
            return str1.equalsIgnoreCase(str2);
        }
        return false;
    }

    public static boolean equalsAny(String str, String... compareValues) {
        if (str == null || compareValues == null) {
            return false;
        }

        for (String value : compareValues) {
            if (equals(str, value)) {
                return true;
            }
        }
        return false;
    }

    public static String emptyIfNull(String chineseText) {
        return chineseText == null ? "" : chineseText;
    }
}