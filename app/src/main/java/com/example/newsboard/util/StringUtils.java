package com.example.newsboard.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 *     author : zdf
 *     time   : 2020/11/21
 * </pre>
 */
public final class StringUtils {
    private static final String LOCAL_IMG_URL_PREFIX = "\n![](file:///android_asset/img/";
    private static final String IMG_PATTERN = "\n.*?(!\\[\\s*(.*?)\\s*]\\(\\s*(\\S*?)(\\s+(['\"])(.*?)\\5)?\\s*?\\))";

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
