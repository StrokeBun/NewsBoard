package com.example.newsboard.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * <pre>
 *     author : Su Songfeng
 *     e-mail : 1986553865@qq.com
 *     time   : 2020/11/19 15:32
 *     desc   : 文件工具类
 *     version: 1.0
 * </pre>
 */
public class FileUtils {

    private static final String DEFAULT_CHARTSET_NAME = "UTF-8";
    /**
     * 读取并以UTF-8的格式返回文件内容
     * @param inputStream 输入流
     * @return 文本内容字符串
     * @throws IOException io错误将抛出IOException
     */
    public static String readFile(InputStream inputStream) throws IOException{
            int length = inputStream.available();
            byte[] buffer = new byte[length];
            inputStream.read(buffer);
            return new String(buffer, DEFAULT_CHARTSET_NAME);
    }

    /**
     * 文件工具类无法被实例化
     */
    private FileUtils() {
        throw new UnsupportedOperationException("FileUtils cant be constructed");
    }
}
