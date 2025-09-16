package cn.tangshh.stock_watcher.util;

import cn.tangshh.stock_watcher.config.PluginConfig;
import com.intellij.DynamicBundle;
import com.intellij.l10n.LocalizationUtil;
import org.jetbrains.annotations.PropertyKey;

/**
 * 国际化工具类
 *
 * @author Tang
 * @version v1.0
 */
public class I18nUtil {

    private static final String BUNDLE = "messages.PluginBundle";
    private static final DynamicBundle INSTANCE = new DynamicBundle(I18nUtil.class, BUNDLE);
    private static final PluginConfig config = PluginConfig.getInstance();

    private I18nUtil() {
    }

    /**
     * 获取国际化消息
     *
     * @param key    消息键
     * @param params 消息参数
     * @return 国际化后的消息
     */
    public static String message(@PropertyKey(resourceBundle = BUNDLE) String key, Object... params) {
        return INSTANCE.getMessage(key, params);
    }

    public static String autoPinyin(@PropertyKey(resourceBundle = BUNDLE) String key, Object... params) {
        String msg = INSTANCE.getMessage(key, params);
        if (isChinese() && config.getPrivacyModeEnabled()) {
            msg = PinyinUtil.getPinyin(msg);
        }
        return msg;
    }

    /**
     * 获取当前语言
     *
     * @return 当前语言代码
     */
    public static String lang() {
        return LocalizationUtil.INSTANCE.getLocale().getLanguage();
    }

    /**
     * 是否为中文环境
     *
     * @return 是否为中文环境
     */
    public static boolean isChinese() {
        return "zh".equals(lang());
    }

}