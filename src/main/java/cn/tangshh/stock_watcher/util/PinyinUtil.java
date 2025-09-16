package cn.tangshh.stock_watcher.util;

import com.hankcs.hanlp.HanLP;

/**
 * 拼音工具类
 *
 * @author Tang
 */
public class PinyinUtil {
    private PinyinUtil() {
    }

    /**
     * 将中文字符串转换为拼音
     *
     * @param chineseText 中文字符串
     * @return 对应的拼音字符串
     */
    public static String getPinyin(String chineseText) {
        if (chineseText == null || chineseText.isEmpty()) {
            return "";
        }
        // 使用 HanLP 转换为拼音，用空格分隔，保留声调
        String pinyinWithSpaces = HanLP.convertToPinyinString(chineseText, " ", true);
        // 按空格拆分，然后对每个拼音单词首字母大写
        StringBuilder result = new StringBuilder();
        String[] pinyinArray = pinyinWithSpaces.split(" ");
        for (String pinyin : pinyinArray) {
            if (!pinyin.isEmpty()) {
                String lowercase = pinyin.toLowerCase();
                result.append(Character.toTitleCase(lowercase.charAt(0)))
                        .append(lowercase.substring(1));
            }
        }
        return result.toString();
    }
}