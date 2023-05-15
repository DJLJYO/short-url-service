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

    /**
     * 更新，通过Url地址及绑定的域名ID进行更新
     *
     * @param shortenRequest 请求上来的Body
     * @return 更新记录,返回null表示没有更新或找不到记录
     */
    ShortDefinitional updateByUrlAndDomain(ShortenRequest shortenRequest);
}
