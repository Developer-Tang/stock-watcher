package cn.tangshh.stock_watcher.enums;

import cn.tangshh.stock_watcher.constant.I18nKey;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 *
 * @author Tang
 * @version v1.0
 */
@Getter
@AllArgsConstructor
public enum PopupDetailView implements IEnum, I18nKey {
    TIME_SHARE(POPUP_UI_TIME_SHARE),
    DAY_K(POPUP_UI_DAY_K),
    WEEK_K(POPUP_UI_WEEK_K),
    MONTH_K(POPUP_UI_MONTH_K),
    DETAIL(POPUP_UI_DETAIL),
    ;

    private final String i18nKey;
}