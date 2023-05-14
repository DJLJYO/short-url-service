package com.fasturl.shorturlservice.controller;

import com.fasturl.shorturlservice.model.Domain;
import com.fasturl.shorturlservice.model.Result;
import com.fasturl.shorturlservice.model.ShortDefinitional;
import com.fasturl.shorturlservice.model.request.ShortenRequest;
import com.fasturl.shorturlservice.service.DomainService;
import com.fasturl.shorturlservice.service.ShortDefinitionalService;
import com.fasturl.shorturlservice.utils.EncodeShortUrl;
import com.fasturl.shorturlservice.utils.HttpServletRequestUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

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

    @Resource
    private DomainService domainService;

    /**
     * 重定向跳转
     *
     * @param key      重定向的URL Key
     * @param response 响应头
     * @return Result
     */
    @GetMapping("/{key}")
    public Result sendRedirect(@PathVariable String key, HttpServletResponse response, HttpServletRequest request) {
        System.out.println(request.getServerName());
        ShortDefinitional shortDefinitional = shortDefinitionalService.queryOriginUrl(key);
        // 数据库有记录
        if (shortDefinitional != null) {
            // 地址失效
            if (shortDefinitional.getStatus() == ShortDefinitional.STATUS_INVALID){
                return Result.fail("地址已失效。");
            }
            // 判断是否过期(超出有效日期)
            if(shortDefinitional.getExpireDate().getTime() < new Date().getTime()){
                // 更新地址Status状态
                shortDefinitional.setStatus(ShortDefinitional.STATUS_INVALID);
                shortDefinitionalService.updateById(shortDefinitional);
                return Result.fail("地址已过期。");
            }
            Domain domain = domainService.queryById(shortDefinitional.getDomainId());
            // 以下代码可优化
            if (domain != null && domain.getDomain().equals(HttpServletRequestUtil.getServerNamePort(request))){
                String redirectUrl = shortDefinitional.getOriginUrl();
                // 301重定向
                response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
                response.setHeader("location", redirectUrl);
                return Result.success(shortDefinitional, "地址已重定向");
            }
        }
        return Result.fail("找不到该地址！");
    }

    /**
     * 缩短网址
     *
     * @param shortenRequest 缩短网址请求体
     * @return Result
     */
    @RequestMapping("/shorten")
    public Result shortenUrl(ShortenRequest shortenRequest) {
        if (!EncodeShortUrl.isUrl(shortenRequest.getUrl())) {
            return Result.fail("不是有效的URL！");
        }
        return shortDefinitionalService.addUrl(shortenRequest);
    }
}
