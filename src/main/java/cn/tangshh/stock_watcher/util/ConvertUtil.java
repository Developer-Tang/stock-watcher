package cn.tangshh.stock_watcher.util;

import java.math.BigDecimal;

/**
 *
 *
 * @author Tang
 * @version v1.0
 */

public final class ConvertUtil {
    private ConvertUtil() {
    }

    public static BigDecimal toBigDecimal(String s) {
        if (StrUtil.nonBlank(s)) {
            try {
                return new BigDecimal(s.trim());
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    public static BigDecimal toBigDecimal(String s, BigDecimal defaultValue) {
        if (StrUtil.nonBlank(s)) {
            try {
                return new BigDecimal(s.trim());
            } catch (Exception ignored) {
            }
        }
        return defaultValue;
    }

    public static Integer toInt(String s) {
        return toInt(s, null);
    }

    public static Integer toInt(String s, Integer defaultValue) {
        if (StrUtil.nonBlank(s)) {
            try {
                return Integer.parseInt(s.trim());
            } catch (Exception ignored) {
            }
        }
        return defaultValue;
    }
}