package cn.tangshh.stock_watcher.enums;

import cn.tangshh.stock_watcher.constant.I18nKey;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 收益显示样式
 *
 * @author Tang
 */
@Getter
@AllArgsConstructor
public enum DisplayStyle implements IEnum, I18nKey {
    RED_GREEN(CONFIG_DISPLAY_STYLE_RED_GREEN),
    BLACK_GRAY(CONFIG_DISPLAY_STYLE_BLACK_GRAY);

    private final String i18nKey;
}