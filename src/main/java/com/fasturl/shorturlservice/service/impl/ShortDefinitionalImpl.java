package com.fasturl.shorturlservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasturl.shorturlservice.mapper.ShortDefinitionalMapper;
import com.fasturl.shorturlservice.model.Domain;
import com.fasturl.shorturlservice.model.Result;
import com.fasturl.shorturlservice.model.ShortDefinitional;
import com.fasturl.shorturlservice.model.request.ShortenRequest;
import com.fasturl.shorturlservice.service.DomainService;
import com.fasturl.shorturlservice.service.ShortDefinitionalService;
import com.fasturl.shorturlservice.utils.EncodeShortUrl;
import com.fasturl.shorturlservice.utils.Encrypt;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author quanyi
 * @since 2022-12-8 17:36:15
 */
@Service
public class ShortDefinitionalImpl extends ServiceImpl<ShortDefinitionalMapper, ShortDefinitional> implements ShortDefinitionalService {

    @Resource
    private DomainService domainService;

    @Override
    public Result addUrl(ShortenRequest shortenRequest) {
        ShortDefinitional shortDefinitional = new ShortDefinitional();
        // 检查域名ID
        Domain domain = domainService.queryById(shortenRequest.getDomain());
        if (domain == null) {
            // 域名不存在
            return Result.fail("domain id not exits!");
        }
        // 取URL摘要
        String urlMd5 = Encrypt.md5(shortenRequest.getUrl());
        shortDefinitional.setOriginUrlMd5(urlMd5);
        LambdaQueryWrapper<ShortDefinitional> queryWrapper = new LambdaQueryWrapper<>();
        // md5相同就说明两个URL是相同的
        queryWrapper.eq(ShortDefinitional::getOriginUrlMd5, urlMd5);
        ShortDefinitional sd = baseMapper.selectOne(queryWrapper);
        // 数据库有记录
        if (sd != null) {
            return Result.success(domain.getDomain() + "/" + sd.getShortKey());
        }
        // 验证short url关键词
        String shortKey = EncodeShortUrl.shortUrlKey(urlMd5);
        do {
            queryWrapper.clear();
            queryWrapper.eq(ShortDefinitional::getShortKey, shortKey);
            // 查short url关键词是否重复
            sd = baseMapper.selectOne(queryWrapper);
            if (sd == null) {
                // 无重复，跳过
                continue;
            }
            // 加盐
            urlMd5 = Encrypt.md5(shortenRequest.getUrl() + new Date().getTime());
            shortKey = EncodeShortUrl.shortUrlKey(urlMd5);
            shortDefinitional.setMd5Composite(1);
//            System.out.println(shortenRequest.getUrl());
        } while (sd != null);
        // 短URL
        shortDefinitional.setShortKey(shortKey);
        // 源地址
        shortDefinitional.setOriginUrl(shortenRequest.getUrl());
        // 有效日期
        shortDefinitional.setExpireDate(shortenRequest.getExpireDate());
        // 域名ID
        shortDefinitional.setDomainId(shortenRequest.getDomain());
        if (baseMapper.insert(shortDefinitional) == 1) {
            String shortUrl = domain.getDomain() + "/" + shortDefinitional.getShortKey();
            return Result.success(shortUrl);
        }
        return Result.fail("insert fail!");
    }

    @Override
    public ShortDefinitional queryOriginUrl(String shortKey) {
        LambdaQueryWrapper<ShortDefinitional> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(ShortDefinitional::getShortKey, shortKey);
        ShortDefinitional shortDefinitionalList = baseMapper.selectOne(lambdaQueryWrapper);
        return shortDefinitionalList;
    }
}
