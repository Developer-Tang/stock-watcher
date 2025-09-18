package cn.tangshh.stock_watcher.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 *
 * @author Tang
 * @version v1.0
 */

public final class DateUtil {
    private static final String DEFAULT_TIME_PATTERN = "HH:mm:ss";
    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
    private static final String DEFAULT_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final SimpleDateFormat DEFAULT_TIME_FORMAT = new SimpleDateFormat(DEFAULT_TIME_PATTERN);
    private static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
    private static final SimpleDateFormat DEFAULT_DATE_TIME_FORMAT = new SimpleDateFormat(DEFAULT_DATE_TIME_PATTERN);

    private DateUtil() {}

    public static Date date() {
        return new Date();
    }

    public static String now() {
        return DEFAULT_DATE_TIME_FORMAT.format(date());
    }

    public static String nowDate() {
        return DEFAULT_DATE_FORMAT.format(date());
    }

    public static String nowTime() {
        return DEFAULT_TIME_FORMAT.format(date());
    }

}