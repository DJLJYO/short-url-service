package com.fasturl.shorturlservice.controller;

import com.fasturl.shorturlservice.model.Domain;
import com.fasturl.shorturlservice.model.Result;
import com.fasturl.shorturlservice.model.request.DomainRequest;
import com.fasturl.shorturlservice.service.DomainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 域名管理
 *
 * @author quanyi
 * @since 2022-12-24 14:53:47
 */
@RestController
@RequestMapping("/domain")
@Api(tags = "域名管理")
public class DomainController {
    @Resource
    private DomainService domainService;

    /**
     * 添加一个域名
     *
     * @param domainRequest Domain
     * @return Result
     */
    @ApiOperation(value = "添加域名", notes = "添加一个域名。在缩短网址时需要指定的", response = Domain.class)
    @PostMapping("/add")
    public Result addDomain(DomainRequest domainRequest) {
        return domainService.addDomain(domainRequest);
    }

    /**
     * 查域名的ID
     *
     * @param domain 域名
     * @return Result
     */
    @ApiOperation(value = "查询域名", notes = "根据域名来查询对应的域名ID", response = Result.class)
    @PostMapping("/queryByDomain")
    public Result queryByDomain(String domain) {
        Domain domainObject = domainService.queryByDomain(domain);
        if (domainObject == null) {
            return Result.fail("找不到此域名ID");
        }
        return Result.success(domainObject);
    }
}
