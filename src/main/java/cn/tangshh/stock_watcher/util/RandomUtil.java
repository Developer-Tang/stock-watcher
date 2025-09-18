package cn.tangshh.stock_watcher.util;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机工具类
 *
 * @author Tang
 * @version v1.0
 */

public final class RandomUtil {
    /** 用于随机选的数字 */
    public static final String BASE_NUMBER = "0123456789";
    /** 用于随机选的字符 */
    public static final String BASE_CHAR = "abcdefghijklmnopqrstuvwxyz";
    /** 用于随机选的字符和数字（小写） */
    public static final String BASE_CHAR_NUMBER_LOWER = BASE_CHAR + BASE_NUMBER;
    /** 用于随机选的字符和数字（包括大写和小写字母） */
    public static final String BASE_CHAR_NUMBER = BASE_CHAR.toUpperCase() + BASE_CHAR_NUMBER_LOWER;

    private RandomUtil() {}

    public static ThreadLocalRandom getRandom() {
        return ThreadLocalRandom.current();
    }

    public static int randomInt(final int limitExclude) {
        return getRandom().nextInt(limitExclude);
    }

    public static String randomStr(final int length) {
        StringBuilder builder = new StringBuilder(length);
        int strLength = BASE_CHAR_NUMBER.length();
        for (int i = 0; i < length; i++) {
            int index = randomInt(strLength);
            builder.append(BASE_CHAR_NUMBER.charAt(index));
        }
        return builder.toString();
    }
}