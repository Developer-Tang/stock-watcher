package cn.tangshh.stock_watcher.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * coll工具类
 *
 * @author Tang
 * @version v1.0
 */

public final class CollUtil {
    private CollUtil() {}

    public static <T, R> List<R> map(Iterable<T> it, Function<? super T, ? extends R> func) {
        return map(it, func, false);
    }

    public static <T, R> List<R> map(Iterable<T> it, Function<? super T, ? extends R> func, boolean ignoreNull) {
        List<R> result = new ArrayList<>();
        if (null != it) {
            for (T t : it) {
                if (null != t || !ignoreNull) {
                    R value = func.apply(t);
                    if (null != value || !ignoreNull) {
                        result.add(value);
                    }
                }
            }
        }
        return result;
    }

    public static <T> T findOne(Iterable<T> collection, Function<? super T, Boolean> func) {
        if (null != collection) {
            for (T t : collection) {
                if (Boolean.TRUE.equals(func.apply(t))) {
                    return t;
                }
            }
        }
        return null;
    }
}