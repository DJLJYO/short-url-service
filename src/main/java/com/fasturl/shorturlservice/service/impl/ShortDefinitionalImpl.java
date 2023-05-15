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
        ShortDefinitional shortDefinitional;
        // 检查域名ID
        Domain domain = domainService.queryById(shortenRequest.getDomain());
        if (domain == null) {
            // 域名不存在
            return Result.fail("domain id not exits!");
        }
        // 返回对象说明更新成功，表示数据库有数据
        if ((shortDefinitional = updateByUrlAndDomain(shortenRequest)) != null) {
            // 数据更新了直接返回结果
            String shortUrl = domain.getDomain() + "/" + shortDefinitional.getShortKey();
            return Result.success(shortUrl);
        }
        // 取URL摘要
        String urlMd5 = Encrypt.md5(shortenRequest.getUrl());
        LambdaQueryWrapper<ShortDefinitional> queryWrapper = new LambdaQueryWrapper<>();
        // 验证short url关键词
        String shortKey = EncodeShortUrl.shortUrlKey(urlMd5);
        // urlMd5 加盐处理
        String urlMd5Salting = "";
        // 下面do while这一部分主要是处理解决shortKey重复
        do {
            queryWrapper.clear();
            queryWrapper.eq(ShortDefinitional::getShortKey, shortKey);
            // 查short url关键词是否重复，查不到就是没重复
            shortDefinitional = baseMapper.selectOne(queryWrapper);
            if (shortDefinitional == null) {
                // 无重复，跳过
                continue;
            }
            // 加盐
            urlMd5Salting = Encrypt.md5(shortenRequest.getUrl() + new Date().getTime());
            shortKey = EncodeShortUrl.shortUrlKey(urlMd5Salting);
//            System.out.println(shortenRequest.getUrl());
        } while (shortDefinitional != null);
        shortDefinitional = new ShortDefinitional();
        // 被加盐了
        if (!urlMd5Salting.equals("")){
            shortDefinitional.setMd5Composite(1);
        }
        // url md5
        shortDefinitional.setOriginUrlMd5(urlMd5);
        // 短URL
        shortDefinitional.setShortKey(shortKey);
        // 源地址
        shortDefinitional.setOriginUrl(shortenRequest.getUrl());
        // 有效日期
        shortDefinitional.setExpireDate(shortenRequest.getExpireDate());
        // 域名ID
        shortDefinitional.setDomainId(shortenRequest.getDomain());
        // 域名状态:有效
        shortDefinitional.setStatus(ShortDefinitional.STATUS_VALIDITY);
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

    @Override
    public ShortDefinitional updateByUrlAndDomain(ShortenRequest shortenRequest) {
        // 取URL摘要
        String urlMd5 = Encrypt.md5(shortenRequest.getUrl());
        LambdaQueryWrapper<ShortDefinitional> queryWrapper = new LambdaQueryWrapper<>();
        // Url
        queryWrapper.eq(ShortDefinitional::getOriginUrlMd5, urlMd5);
        // 绑定的域名
        queryWrapper.eq(ShortDefinitional::getDomainId, shortenRequest.getDomain());
        ShortDefinitional shortDefinitional = baseMapper.selectOne(queryWrapper);
        // 查询不到记录
        if (shortDefinitional == null) {
            return null;
        }
        // 有效期必须大于现行时间
        if (shortenRequest.getExpireDate().getTime() <= new Date().getTime()) {
            return null;
        }
        // 有效期
        shortDefinitional.setExpireDate(shortenRequest.getExpireDate());
        // 当有效期大于当前时间时，域名状态改为有效(可用)
        shortDefinitional.setStatus(ShortDefinitional.STATUS_VALIDITY);
        // 更新成功直接返回 ShortDefinitional对象，失败返回null
        return baseMapper.updateById(shortDefinitional) > 0 ? shortDefinitional : null;
    }
}
