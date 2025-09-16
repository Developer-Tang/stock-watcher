package cn.tangshh.stock_watcher.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 股票配置信息
 *
 * @author Tang
 */
@Data
@Accessors(chain = true)
public class StockConfig {
    private String code;
    private BigDecimal costPrice;
    private Integer holdQuantity;
}