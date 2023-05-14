package com.fasturl.shorturlservice.model.request;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 缩短网址请求体
 *
 * @author quanyi
 * @since 2022-12-25 18:10:37
 */
@Data
public class ShortenRequest {
    // 要缩短的URL
    private String url;
    // 前缀域名ID
    private int domain;
    // 有效日期
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expireDate;
    private String key;
}
