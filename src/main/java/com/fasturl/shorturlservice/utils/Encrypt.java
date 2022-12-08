package com.fasturl.shorturlservice.utils;

import java.security.MessageDigest;

/**
 * Encrypt
 *
 * @see <a href="https://www.cnblogs.com/wangyangliuping/p/5855360.html#_label3">短链(ShortURL)的Java实现</a>
 * @see <a href="https://stackoverflow.com/questions/742013/how-do-i-create-a-url-shortener">How do I create a URL shortener?</a>
 * @since 2022-12-8 13:36:37
 */
public class Encrypt {
    // 十六进制下数字到字符的映射数组
    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};

    /**
     * md5加密32位
     *
     * @param inputStr
     * @return
     */
    public static String md5(String inputStr) {
        return encodeByMD5(inputStr);
    }

    /**
     * 对字符串进行MD5编码
     *
     * @param originString
     * @return
     */
    private static String encodeByMD5(String originString) {
        if (originString != null) {
            try {
                // 创建具有指定算法名称的信息摘要
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                // 使用指定的字节数组对摘要进行最后更新，然后完成摘要计算
                byte[] results = md5.digest(originString.getBytes());
                // 将得到的字节数组变成字符串返回
                String result = byteArrayToHexString(results);
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 轮换字节数组为十六进制字符串
     *
     * @param b 字节数组
     * @return 十六进制字符串
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    /**
     * 将一个字节转化成十六进制形式的字符串
     *
     * @param b
     * @return
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }
}
