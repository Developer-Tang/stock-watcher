package cn.tangshh.stock_watcher.constant;

/**
 *
 *
 * @author Tang
 * @version v1.0
 */

public interface I18nKey {
    /* -------------------- 配置相关 -------------------- */
    /** 配置标题 */
    String CONFIG_TITLE = "config.title";
    /** 配置标题 - 收入显示模式 */
    String CONFIG_TITLE_REVENUE_DISPLAY_MODEL = "config.title.revenue_display_model";
    /** 配置 - 显示风格红绿 */
    String CONFIG_DISPLAY_STYLE_RED_GREEN = "config.display_style.red_green";
    /** 配置 - 显示风格黑灰色 */
    String CONFIG_DISPLAY_STYLE_BLACK_GRAY = "config.display_style.black_gray";
    /** 配置说明 - 收入显示模式 */
    String CONFIG_COMMENT_REVENUE_DISPLAY_MODEL = "config.comment.revenue_display_model";
    /** 配置标题 - 刷新周期 */
    String CONFIG_TITLE_REFRESH_CYCLE = "config.title.refresh_cycle";
    /** 配置说明 - 刷新周期 */
    String CONFIG_COMMENT_REFRESH_CYCLE = "config.comment.refresh_cycle";
    /** 配置标题 - 数据源 */
    String CONFIG_TITLE_DATA_SOURCE = "config.title.data_source";
    /** 配置标题 - 股票列表 */
    String CONFIG_TITLE_STOCK_LIST = "config.title.stock_list";
    /** 配置说明 - 股票列表 */
    String CONFIG_COMMENT_STOCK_LIST = "config.comment.stock_list";
    /** 配置标题 - 股票字段 */
    String CONFIG_TITLE_STOCK_FIELDS = "config.title.stock_fields";
    /** 配置说明 - 股票字段 */
    String CONFIG_COMMENT_STOCK_FIELDS = "config.comment.stock_fields";
    /** 配置标题 - 隐私模式 */
    String CONFIG_TITLE_PRIVACY_MODE = "config.title.privacy_mode";
    /** 配置说明 - 隐私模式 */
    String CONFIG_COMMENT_PRIVACY_MODE = "config.comment.privacy_mode";
    /** 配置错误 - 刷新周期 */
    String CONFIG_ERROR_REFRESH_CYCLE = "config.error.refresh_cycle";

    /* -------------------- 工具窗口相关 -------------------- */
    /** 工具窗口 - 刷新按钮 */
    String TOOL_WINDOW_REFRESH_BTN = "tool_window.refresh_btn";
    /** 工具窗口 - 停止按钮 */
    String TOOL_WINDOW_STOP_BTN = "tool_window.stop_btn";
    /** 工具窗口 - 重置样式按钮 */
    String TOOL_WINDOW_RESET_TABLE_STYLE_BTN = "tool_window.reset_table_style_btn";
    /** 工具窗口 - 刷新成功说明 */
    String TOOL_WINDOW_REFRESH_OK_MSG = "tool_window.refresh_ok_msg";
    /** 工具窗口 - 等待刷新说明 */
    String TOOL_WINDOW_REFRESH_WAIT_MSG = "tool_window.refresh_wait_msg";
    /** 工具窗口 - 刷新失败说明 */
    String TOOL_WINDOW_REFRESH_FAIL_MSG = "tool_window.refresh_fail_msg";
    /** 工具窗口 - 停止刷新说明 */
    String TOOL_WINDOW_REFRESH_STOP_MSG = "tool_window.refresh_stop_msg";

    /* -------------------- 弹窗相关 -------------------- */
    /** 弹窗 - 标题 */
    String POPUP_UI_TITLE = "popup_ui.title";
    /** 弹窗 - 分时 */
    String POPUP_UI_TIME_SHARE = "popup_ui.time_share";
    /** 弹窗 - 日K */
    String POPUP_UI_DAY_K = "popup_ui.day_k";
    /** 弹窗 - 周K */
    String POPUP_UI_WEEK_K = "popup_ui.week_k";
    /** 弹窗 - 月K */
    String POPUP_UI_MONTH_K = "popup_ui.month_k";
    /** 弹窗 - 年K */
    String POPUP_UI_YEAR_K = "popup_ui.year_k";
    /** 弹窗 - 详情 */
    String POPUP_UI_DETAIL = "popup_ui.detail";

    /* -------------------- 股票字段相关 -------------------- */
    /** 字段 - 标识 */
    String FIELD_CODE = "field.code";
    /** 字段 - 名字 */
    String FIELD_NAME = "field.name";
    /** 字段 - 上一收盘价 */
    String FIELD_PREV_CLOSE_PRICE = "field.prev_close_price";
    /** 字段 - 开盘价 */
    String FIELD_OPEN_PRICE = "field.open_price";
    /** 字段 - 低价 */
    String FIELD_LOW_PRICE = "field.low_price";
    /** 字段 - 高价 */
    String FIELD_HIGH_PRICE = "field.high_price";
    /** 字段 - 当前价格 */
    String FIELD_CURRENT_PRICE = "field.current_price";
    /** 字段 - 价格变化 */
    String FIELD_PRICE_CHANGE = "field.price_change";
    /** 字段 - 价格变化百分比 */
    String FIELD_PRICE_CHANGE_PERCENT = "field.price_change_percent";
    /** 字段 - 价格变化利润 */
    String FIELD_PRICE_CHANGE_PROFIT = "field.price_change_profit";
    /** 字段 - 持有利润 */
    String FIELD_HOLDING_PROFIT = "field.holding_profit";
    /** 字段 - 持有利润百分比 */
    String FIELD_HOLDING_PROFIT_PERCENT = "field.holding_profit_percent";

    /* -------------------- 配置相关 -------------------- */
    /** 数据来源 -  新浪 */
    String DATA_SOURCE_SINA = "data_source.sina";
    String DATA_SOURCE_TENCENT = "data_source.tencent";

    /* -------------------- 通用 -------------------- */
    String OPERATE_FALL = "operate.fall";
}