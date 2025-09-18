package cn.tangshh.stock_watcher.util;

/**
 * 数组工具类
 *
 * @author Tang
 * @version v1.0
 */
public final class ArrayUtil {
    private ArrayUtil() {}

    public static <T> T get(T[] arr, int index) {
        if (arr == null || index < 0 || index >= arr.length) {
            return null;
        }
        return arr[index];
    }

    public static <T> T get(T[] arr, int index, T defaultValue) {
        T t = get(arr, index);
        return t != null ? t : defaultValue;
    }
}