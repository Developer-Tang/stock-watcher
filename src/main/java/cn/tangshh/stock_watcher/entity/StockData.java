package cn.tangshh.stock_watcher.entity;

import cn.tangshh.stock_watcher.util.BigDecimalUtil;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 股票数据模型类
 *
 * @author Tang
 */
@Data
@Accessors(chain = true)
public class StockData {
    /** 股票代码 */
    private String code;
    /** 股票名称 */
    private String name;
    /** 昨日收盘价 */
    private BigDecimal prevClosePrice;
    /** 今日开盘价 */
    private BigDecimal openPrice;
    /** 今日最低价 */
    private BigDecimal lowPrice;
    /** 今日最高价 */
    private BigDecimal highPrice;
    /** 当前价 */
    private BigDecimal currentPrice;
    /** 成本价 */
    private BigDecimal costPrice = BigDecimal.ZERO;
    /** 持仓数量 */
    private Integer holdQuantity = 0;

    /** 获取当前涨跌金额 */
    public BigDecimal getPriceChange() {
        if (currentPrice.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return BigDecimalUtil.subtract(currentPrice, prevClosePrice);
    }

    /** 获取当前涨跌百分比 */
    public BigDecimal getPriceChangePercent() {
        if (prevClosePrice.compareTo(BigDecimal.ZERO) == 0 || getPriceChange().compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return BigDecimalUtil.multiply(BigDecimalUtil.divide(getPriceChange(), prevClosePrice), 100);
    }

    /** 获取当前涨跌收益 */
    public BigDecimal getPriceChangeProfit() {
        if (getPriceChange().compareTo(BigDecimal.ZERO) == 0 || holdQuantity == 0) {
            return BigDecimal.ZERO;
        }
        return BigDecimalUtil.multiply(BigDecimalUtil.subtract(currentPrice, prevClosePrice), holdQuantity);
    }

    /** 获取当前持仓收益金额 */
    public BigDecimal getHoldingProfit() {
        if (currentPrice.compareTo(BigDecimal.ZERO) == 0 || holdQuantity == 0) {
            return BigDecimal.ZERO;
        }
        return BigDecimalUtil.multiply(BigDecimalUtil.subtract(currentPrice, costPrice), BigDecimal.valueOf(holdQuantity));
    }

    /** 获取当前持仓收益百分比 */
    public BigDecimal getHoldingProfitPercent() {
        if (costPrice.compareTo(BigDecimal.ZERO) == 0 || currentPrice.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return BigDecimalUtil.multiply(BigDecimalUtil.divide(BigDecimalUtil.subtract(currentPrice, costPrice), costPrice), 100);
    }


    /* -------------------- 重写get/set方法优化调用 -------------------- */

    public void setCostPrice(BigDecimal costPrice) {
        if (costPrice != null) {
            this.costPrice = costPrice;
        }
    }

    public void setHoldQuantity(Integer holdQuantity) {
        if (holdQuantity != null) {
            this.holdQuantity = holdQuantity;
        }
    }
}