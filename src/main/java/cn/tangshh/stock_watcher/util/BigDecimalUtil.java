package cn.tangshh.stock_watcher.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 大十进制工具类
 *
 * @author Tang
 */
public class BigDecimalUtil {
    private static final RoundingMode DEFAULT_MODE = RoundingMode.HALF_UP;
    private static final int DEFAULT_SCALE = 4;

    private BigDecimalUtil() {
        // 私有构造函数，防止实例化
    }

    /**
     * 加
     *
     * @param v1 值1
     * @param v2 值2
     * @return 计算结果
     */
    public static BigDecimal add(Number v1, Number v2) {
        return new BigDecimal(v1.toString()).add(new BigDecimal(v2.toString()));
    }

    /**
     * 减
     *
     * @param v1 值1
     * @param v2 值2
     * @return 计算结果
     */
    public static BigDecimal subtract(Number v1, Number v2) {
        return new BigDecimal(v1.toString()).subtract(new BigDecimal(v2.toString()));
    }

    /**
     * 乘
     *
     * @param v1 值1
     * @param v2 值2
     * @return 计算结果
     */
    public static BigDecimal multiply(Number v1, Number v2) {
        return new BigDecimal(v1.toString()).multiply(new BigDecimal(v2.toString()));
    }

    /**
     * 除
     *
     * @param v1 值1
     * @param v2 值2
     * @return 计算结果
     */
    public static BigDecimal divide(Number v1, Number v2) {
        return divide(v1, v2, DEFAULT_SCALE, DEFAULT_MODE);
    }

    /**
     * 除
     *
     * @param v1    值1
     * @param v2    值2
     * @param scale 小数位数
     * @param mode  舍入模式
     * @return 计算结果
     */
    public static BigDecimal divide(Number v1, Number v2, int scale, RoundingMode mode) {
        return new BigDecimal(v1.toString()).divide(new BigDecimal(v2.toString()), scale, mode);
    }

    /**
     * 设置比例
     *
     * @param v 值
     * @return 设置后的结果
     */
    public static BigDecimal setScale(Number v) {
        return setScale(v, DEFAULT_SCALE, DEFAULT_MODE);
    }

    /**
     * 设置比例
     *
     * @param v     值
     * @param scale 小数位数
     * @param mode  舍入模式
     * @return 设置后的结果
     */
    public static BigDecimal setScale(Number v, int scale, RoundingMode mode) {
        return new BigDecimal(v.toString()).setScale(scale, mode);
    }
}