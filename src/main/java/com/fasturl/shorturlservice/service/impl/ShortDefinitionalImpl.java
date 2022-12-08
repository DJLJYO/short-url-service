package com.fasturl.shorturlservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasturl.shorturlservice.mapper.ShortDefinitionalMapper;
import com.fasturl.shorturlservice.model.Result;
import com.fasturl.shorturlservice.model.ShortDefinitional;
import com.fasturl.shorturlservice.service.ShortDefinitionalService;
import com.fasturl.shorturlservice.utils.EncodeShortUrl;
import org.springframework.stereotype.Service;

/**
 * @author quanyi
 * @since 2022-12-8 17:36:15
 */
@Service
public class ShortDefinitionalImpl extends ServiceImpl<ShortDefinitionalMapper, ShortDefinitional> implements ShortDefinitionalService {
    @Override
    public Result addUrl(String url) {
        String shortKey = EncodeShortUrl.shortUrl(url);
        ShortDefinitional shortDefinitional = new ShortDefinitional();
        // 短URL
        shortDefinitional.setShortKey(shortKey);
        // 源地址
        shortDefinitional.setOriginUrl(url);
        LambdaQueryWrapper<ShortDefinitional> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShortDefinitional::getShortKey, shortDefinitional.getShortKey());
        ShortDefinitional sd = baseMapper.selectOne(queryWrapper);
        if (sd != null) {
            return Result.success("http://localhost:8080/" + sd.getShortKey());
        }
        if (baseMapper.insert(shortDefinitional) == 1) {
            String shortUrl = "http://localhost:8080/" + shortDefinitional.getShortKey();
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
