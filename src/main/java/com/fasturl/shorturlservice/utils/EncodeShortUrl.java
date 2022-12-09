package com.fasturl.shorturlservice.utils;

/**
 * URL缩短生成类
 *
 * @see <a href="https://www.cnblogs.com/wangyangliuping/p/5855360.html#_label3">短链(ShortURL)的Java实现</a>
 * @see <a href="https://stackoverflow.com/questions/742013/how-do-i-create-a-url-shortener">How do I create a URL shortener?</a>
 * @since 2022-12-8 13:48:40
 */
public class EncodeShortUrl {
    // 要使用生成 URL 的字符
    private static final String[] CHARS = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
            "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
            "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"
    };

    // 生成成员数
    private static final int MEMBER = 2;

    // 成员字符长度
    private static final int LENGTH = 4;

    /**
     * 缩短网址
     *
     * @param md5 网址MD5
     * @return 生成的关键词
     */
    public static String shortUrlKey(String md5) {
        String[] urlKeys = shortUrlKey(md5, MEMBER, LENGTH);

        // 此处固定长 8
        String key = urlKeys[0] + urlKeys[1];
        return key;
    }

    /**
     * 缩短网址
     *
     * @param md5    网址MD5
     * @param member 数组成员数
     * @param length 成员字符长度
     * @return
     */
    public static String[] shortUrlKey(String md5, int member, int length) {
        String encryptMd5 = md5;

        // 只取16位MD5
        String hex = encryptMd5.substring(8, 24);
        String[] resUrl = new String[member];
        for (int i = 0; i < member; i++) {
            // 把加密字符按照8位一组16进制与0x3FFFFFFF进行位与运算
            String sTempSubString = hex.substring(i * 8, i * 8 + 8);

            // 这里需要使用 long 型来转换，因为 Inteter.parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用 long ，则会越界
            long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
            String outChars = "";
            for (int j = 0; j < length; j++) {
                long index = 0x0000003D & lHexLong;     // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
                outChars += CHARS[(int) index];         // 把取得的字符相加
                lHexLong = lHexLong >> 6;             // 每次循环按位右移
            }
            resUrl[i] = outChars;                       // 把字符串存入对应索引的输出数组
        }
        return resUrl;
    }
}
