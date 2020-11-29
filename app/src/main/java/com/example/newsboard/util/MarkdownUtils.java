package com.example.newsboard.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 *     author : Zhong DeFeng
 *     e-mail : 1756809298@qq.com
 *     time   : 2020/11/19 15:32
 *     desc   : markdown文本工具类
 *     version: 1.0
 * </pre>
 */
public final class MarkdownUtils {
    // 本地图片存储路径
    private static final String LOCAL_IMG_URL_PREFIX = "\n![](file:///android_asset/img/";
    // 匹配markdown图片的正则表达式
    private static final String IMG_PATTERN = "\n.*?(!\\[\\s*(.*?)\\s*]\\(\\s*(\\S*?)(\\s+(['\"])(.*?)\\5)?\\s*?\\))";

    /** 将markdown文本中的图片路径映射到本地
     * @param str 未进行路径映射markdown文本字符串
     * @return 路径映射后后的markdown文本字符串
     */
    public static String handleMarkdown(String str) {
        Matcher matcherImage = Pattern.compile(IMG_PATTERN).matcher(str);
        StringBuilder result = new StringBuilder(str);
        int offset = 0;
        while (matcherImage.find()) {
            String imgUrl = matcherImage.group(3);
            imgUrl = removeUnderline(imgUrl);
            StringBuilder sb = new StringBuilder(LOCAL_IMG_URL_PREFIX);
            sb.append(imgUrl);
            sb.append(")\n");
            result.replace(matcherImage.start()+offset, matcherImage.end()+offset, sb.toString());
            // 计算替换前后字符串的长度偏差
            String origin = matcherImage.group(0);
            offset = offset + sb.length() - origin.length();
        }
        return result.toString();
    }

    /**
     * 去除字符串中的下划线
     * @param str 待去除下划线的字符串
     * @return 去除下划线后的字符串
     */
    private static String removeUnderline(String str) {
        String[] arr = str.split("_");
        StringBuilder sb = new StringBuilder();
        for (String s : arr) {
            sb.append(s);
        }
        return sb.toString();
    }

    /**
     * markdown工具类无法被实例化
     */
    private MarkdownUtils() {
        throw new UnsupportedOperationException("StringUtils cant be constructed");
    }
}
