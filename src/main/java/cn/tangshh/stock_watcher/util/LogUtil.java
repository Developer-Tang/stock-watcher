package cn.tangshh.stock_watcher.util;

import cn.hutool.core.util.StrUtil;

/**
 * 日志实用工具
 *
 * @author Tang
 * @version v1.0
 */
public class LogUtil {
    private LogUtil() {

    }

    /**
     * 打印
     *
     * @param temp   模板
     * @param params 参数
     * @since v1.0
     */
    public static void print(String temp, Object... params) {
        System.out.println(StrUtil.format(temp, params));
    }

}