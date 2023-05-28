package com.fasturl.shorturlservice.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 请求Body
 *
 * @author quanyi
 * @since 2022-12-24 21:11:11
 */
@ApiModel(value = "域名请求对象", description = "在操作域名管理接口时用到的对象（Body）")
@Data
public class DomainRequest {
    @ApiModelProperty(value = "域名", required = true, example = "baidu.com")
    private String domain;
    // 状态，1：有效  0：失效
    @ApiModelProperty(value = "域名状态", required = true, example = "1", allowableValues = "1,0")
    private int status;
}
