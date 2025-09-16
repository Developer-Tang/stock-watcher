package cn.tangshh.stock_watcher.service;

import cn.tangshh.stock_watcher.enums.DataSource;
import cn.tangshh.stock_watcher.service.impl.SinaStockDataService;

import java.util.HashMap;
import java.util.Map;

/**
 * 股票数据服务工厂
 *
 * @author Tang
 * @version v1.0
 */
public class StockDataServiceFactory {
    private static final Map<DataSource, StockDataService> serviceMap = new HashMap<>();

    static {
        serviceMap.put(DataSource.SINA, new SinaStockDataService());
    }

    private StockDataServiceFactory() {
        // 私有构造函数，防止实例化
    }

    /**
     * 根据数据源获取对应的服务实例
     *
     * @param source 数据源枚举
     * @return 对应的股票数据服务实例
     * @since v1.0
     */
    public static StockDataService get(DataSource source) {
        return serviceMap.get(source);
    }

    /**
     * 检查是否包含指定数据源的服务
     *
     * @param source 数据源枚举
     * @return 是否包含
     * @since v1.0
     */
    public static boolean contains(DataSource source) {
        return serviceMap.containsKey(source);
    }
}