package com.fasturl.shorturlservice.model.request;

import lombok.Data;

/**
 * 请求Body
 *
 * @author quanyi
 * @since 2022-12-24 21:11:11
 */
@Data
public class DomainRequest {
    private String domain;
    // 状态，1：有效  0：失效
    private int status;
}
