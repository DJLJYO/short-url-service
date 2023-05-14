package com.fasturl.shorturlservice.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * domain实体
 *
 * @author quanyi
 * @since 2022-12-24 14:19:26
 */
@Data
public class Domain {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String domain;
    // 状态，1：有效  0：失效
    private int status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
