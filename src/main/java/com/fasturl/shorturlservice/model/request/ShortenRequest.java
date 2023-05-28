package com.fasturl.shorturlservice.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 缩短网址请求体
 *
 * @author quanyi
 * @since 2022-12-25 18:10:37
 */
@ApiModel(value = "缩短网址对象" , description = "缩短网址时，请求的Body")
@Data
public class ShortenRequest {
    // 要缩短的URL
    @ApiModelProperty(value = "要缩短的URL")
    private String url;
    // 前缀域名ID
    @ApiModelProperty(value = "选择域名")
    private int domain;
    // 有效日期
    @ApiModelProperty(value = "有效期" , example = "2099-05-15 00:00:00")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expireDate;
    private String key;
}
