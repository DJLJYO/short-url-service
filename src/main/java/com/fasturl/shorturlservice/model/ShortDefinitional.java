package com.fasturl.shorturlservice.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 主表：映射定义
 *
 * @author quanyi
 * @since 2022-12-8 17:04:08
 */
@Data
public class ShortDefinitional {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String shortKey;
    // shortKey是否加盐,1:加盐 0:常规
    private int md5Composite;
    // 源地址32位MD5
    private String originUrlMd5;
    private String originUrl;
    // 域名_ID
    private int domainId;
    // 状态，1：有效  0：失效
    private int status;
    // 有效日期
    private Date expireDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
