package com.fasturl.shorturlservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 响应类
 *
 * @author quanyi
 * @since 2022-12-8 15:34:49
 */
@Data
@AllArgsConstructor
public class Result {
    private int code;
    private String message;
    private Object data;

    /**
     * 成功响应
     *
     * @param data 数据
     * @return 返回Result实例
     */
    public static Result success(Object data) {
        return new Result(200, "success", data);
    }

    /**
     * 成功响应
     *
     * @param data 数据
     * @return 返回Result实例
     */
    public static Result success(Object data, String message) {
        return new Result(200, message, data);
    }

    /**
     * 失败响应
     *
     * @param message 提示信息
     * @return 返回Result实例
     */
    public static Result fail(String message) {
        return new Result(299, message, null);
    }
}
