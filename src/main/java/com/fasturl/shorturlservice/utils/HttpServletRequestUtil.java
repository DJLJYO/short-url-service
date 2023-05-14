package com.fasturl.shorturlservice.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * HttpServletRequest工具类
 *
 * @since 2023-5-15
 */
public class HttpServletRequestUtil {
    /**
     * 返回服务器加端口号  服务器:端口号
     *
     * @param request    HttpServletRequest
     * @param notDefault 去80端口,端口为80的不加
     * @return
     */
    public static String getServerNamePort(HttpServletRequest request, Boolean notDefault) {
        // 不带80端口的
        if (notDefault) {
            return request.getServerName();
        }
        return request.getServerName() + ":" + request.getServerPort();
    }

    /**
     * 返回服务器加端口号  服务器:端口号  默认80端口不带
     *
     * @param request HttpServletRequest
     * @return
     */
    public static String getServerNamePort(HttpServletRequest request) {
        // 不带80端口的
        if (request.getServerPort() == 80) {
            return getServerNamePort(request, true);
        }
        return getServerNamePort(request, false);
    }
}
