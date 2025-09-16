package cn.tangshh.stock_watcher.constant;

/**
 * 字段
 *
 * @author Tang
 * @date 2025.09.08
 */
public interface StockFieldName {
    /** 标识 */
    String CODE = "code";
    /** 名字 */
    String NAME = "name";
    /** 上一收盘价 */
    String PREV_CLOSE_PRICE = "prevClosePrice";
    /** 开盘价 */
    String OPEN_PRICE = "openPrice";
    /** 低价 */
    String LOW_PRICE = "lowPrice";
    /** 高价 */
    String HIGH_PRICE = "highPrice";
    /** 当前价格 */
    String CURRENT_PRICE = "currentPrice";
    /** 价格变化 */
    String PRICE_CHANGE = "priceChange";
    /** 价格变化百分比 */
    String PRICE_CHANGE_PERCENT = "priceChangePercent";
    /** 价格变化利润 */
    String PRICE_CHANGE_PROFIT = "priceChangeProfit";
    /** 持有利润 */
    String HOLDING_PROFIT = "holdingProfit";
    /** 持有利润百分比 */
    String HOLDING_PROFIT_PERCENT = "holdingProfitPercent";
}