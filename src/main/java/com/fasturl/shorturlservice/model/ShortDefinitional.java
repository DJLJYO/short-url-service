package com.fasturl.shorturlservice.model;

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
    @TableId
    private int id;
    private String shortKey;
    private String originUrl;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
