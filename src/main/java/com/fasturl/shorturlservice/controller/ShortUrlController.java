package com.fasturl.shorturlservice.controller;

import com.fasturl.shorturlservice.model.Result;
import com.fasturl.shorturlservice.model.ShortDefinitional;
import com.fasturl.shorturlservice.service.ShortDefinitionalService;
import com.fasturl.shorturlservice.utils.EncodeShortUrl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * URL缩短控制器
 *
 * @author quanyi
 * @since 2022-12-8 18:47:54
 */
@RestController
public class ShortUrlController {

    @Resource
    ShortDefinitionalService shortDefinitionalService;

    /**
     * 重定向跳转
     *
     * @param key      重定向的URL Key
     * @param response 响应头
     * @return Result
     */
    @GetMapping("/{key}")
    public Result sendRedirect(@PathVariable String key, HttpServletResponse response) {
        ShortDefinitional shortDefinitional = shortDefinitionalService.queryOriginUrl(key);
        // 数据库有记录
        if (shortDefinitional != null) {
            String redirectUrl = shortDefinitional.getOriginUrl();
            // 301重定向
            response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
            response.setHeader("location", redirectUrl);
            return Result.success(shortDefinitional, "地址已重定向");
        }
        return Result.fail("找不到该地址！");
    }

    /**
     * 缩短网址
     *
     * @param url URL编码后的网址
     * @return Result
     */
    @RequestMapping("/shorten")
    public Result shortenUrl(String url) {
        if (!EncodeShortUrl.isUrl(url)) {
            return Result.fail("不是有效的URL！");
        }
        return shortDefinitionalService.addUrl(url);
    }
}
