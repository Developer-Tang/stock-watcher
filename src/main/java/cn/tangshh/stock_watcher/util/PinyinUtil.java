package cn.tangshh.stock_watcher.util;

import com.github.promeg.pinyinhelper.Pinyin;
import com.github.promeg.pinyinhelper.PinyinMapDict;

import java.util.HashMap;
import java.util.Map;

/**
 * 拼音工具类
 *
 * @author Tang
 */
public class PinyinUtil {
    static {
        Pinyin.init(
                Pinyin.newConfig()
                        .with(new PinyinMapDict() {
                            @Override
                            public Map<String, String[]> mapping() {
                                // 创建多音字字典
                                Map<String, String[]> mapping = new HashMap<>();
                                mapping.put("长城", new String[]{"CHANG", "CHENG"});
                                mapping.put("重庆", new String[]{"CHONG", "QING"});
                                mapping.put("六安", new String[]{"LU", "AN"});
                                return mapping;
                            }
                        })
        );
    }

    private PinyinUtil() {
    }

    /**
     * 将中文字符串转换为拼音
     *
     * @param chineseText 中文字符串
     * @return 对应的拼音字符串
     */
    public static String getPinyin(String chineseText) {
        if (StrUtil.isBlank(chineseText)) {
            return StrUtil.emptyIfNull(chineseText);
        }

        StringBuilder result = new StringBuilder();

        String separator = "@#@#@"; // 避免文本中出现
        String pinyin = Pinyin.toPinyin(chineseText, separator);
        String[] split = pinyin.split(separator);
        for (String text : split) {
            if (text.matches("[A-Z]+")) {
                result.append(Character.toUpperCase(text.charAt(0))).append(text.substring(1).toLowerCase());
            } else {
                result.append(text);
            }
        }
        return result.toString();
    }
}