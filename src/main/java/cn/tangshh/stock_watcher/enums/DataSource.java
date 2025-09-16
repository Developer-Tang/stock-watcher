package cn.tangshh.stock_watcher.enums;

import cn.tangshh.stock_watcher.constant.I18nKey;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据来源枚举
 *
 * @author Tang
 */
@Getter
@AllArgsConstructor
public enum DataSource implements IEnum, I18nKey {
    SINA(DATA_SOURCE_SINA);
    // TENCENT(DATA_SOURCE_TENCENT) // 暂未实现

    private final String i18nKey;
}