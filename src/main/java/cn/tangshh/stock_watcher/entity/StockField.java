package cn.tangshh.stock_watcher.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 股票字段
 *
 * @author Tang
 */
@Data
@Accessors(chain = true)
public class StockField {
    /** 字段名 */
    private String name;
    /** i18n键 */
    private String i18nKey;
    /** 可以取消 */
    private Boolean canCancel = true;
    /** 选中 */
    private Boolean selected = true;
    /** 后缀 / 单位 */
    private String suffix = "";
}