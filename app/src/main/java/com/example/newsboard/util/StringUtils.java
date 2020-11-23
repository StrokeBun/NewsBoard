package com.example.newsboard.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Title: StringUtils
 * @Package: util
 * @Description: String工具类，用于处理markdown文本
 * @author: Zhong Defeng
 * @date: 2020/11/19 15:32
 */
public final class StringUtils {
    // 本地图片存储路径
    private static final String LOCAL_IMG_URL_PREFIX = "\n![](file:///android_asset/img/";
    // 匹配markdown图片的正则表达式
    private static final String IMG_PATTERN = "\n.*?(!\\[\\s*(.*?)\\s*]\\(\\s*(\\S*?)(\\s+(['\"])(.*?)\\5)?\\s*?\\))";

    /** 将markdown文本中的图片路径映射到本地
     * @param str markdown文本
     * @return 处理后的markdown文本
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
            sb.append(")");
            result.replace(matcherImage.start()+offset, matcherImage.end()+offset, sb.toString());
            // 计算replace后两字符串的长度偏差
            String origin = matcherImage.group(0);
            offset = offset + sb.length() - origin.length();
        }
        return result.toString();
    }

    /**
     * 去除字符串中的下划线
     * @param str 待处理字符串
     * @return 处理后的字符串
     */
    private static String removeUnderline(String str) {
        String[] arr = str.split("_");
        StringBuilder sb = new StringBuilder();
        for (String s : arr) {
            sb.append(s);
        }
        return sb.toString();
    }

    private StringUtils() {
        throw new UnsupportedOperationException("StringUtils cant be constructed");
    }
}
