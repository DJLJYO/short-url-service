package com.fasturl.shorturlservice.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 响应类
 *
 * @author quanyi
 * @since 2022-12-8 15:34:49
 */
@ApiModel(value = "响应类", description = "响应对象")
@Data
@AllArgsConstructor
public class Result<T> {
    @ApiModelProperty(value = "状态码")
    private int code;
    @ApiModelProperty(value = "响应信息")
    private String message;
    @ApiModelProperty(value = "响应数据（对象）")
    private T data;

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
