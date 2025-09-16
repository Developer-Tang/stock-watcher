package cn.tangshh.stock_watcher.service;

import cn.tangshh.stock_watcher.entity.StockData;
import cn.tangshh.stock_watcher.enums.PopupDetailView;

import java.util.Collections;
import java.util.List;

/**
 * 股票数据服务
 *
 * @author Tang
 * @version v1.0
 */
public abstract class StockDataService {
    protected final String AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36";

    /**
     * 批量获取股票数据
     *
     * @param codes 股票代码列表
     * @return 股票数据列表
     * @since v1.0
     */
    public List<StockData> batchGetStockData(List<String> codes) {
        return Collections.emptyList();
    }

    /**
     * K线IMG
     *
     * @param stockCode 股份代号
     * @param view      视图
     * @return {@link String }
     * @since v1.0
     */
    public String kLineImg(String stockCode, PopupDetailView view) {
        return "";
    }
}