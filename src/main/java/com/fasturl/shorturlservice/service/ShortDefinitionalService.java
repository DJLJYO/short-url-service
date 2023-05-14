package com.fasturl.shorturlservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fasturl.shorturlservice.model.Result;
import com.fasturl.shorturlservice.model.ShortDefinitional;
import com.fasturl.shorturlservice.model.request.ShortenRequest;

/**
 * Service层
 *
 * @author quanyi
 * @since 2022-12-8 17:36:15
 */
public interface ShortDefinitionalService extends IService<ShortDefinitional> {
    /**
     * 增加一条URL
     *
     * @param shortenRequest
     * @return Result
     */
    Result addUrl(ShortenRequest shortenRequest);

    /**
     * 查询源地址
     *
     * @param shortKey short url关键词
     * @return Result
     */
    ShortDefinitional queryOriginUrl(String shortKey);
}
