package com.fasturl.shorturlservice.controller;

import com.fasturl.shorturlservice.model.Result;
import com.fasturl.shorturlservice.model.request.DomainRequest;
import com.fasturl.shorturlservice.service.DomainService;
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
public class DomainController {
    @Resource
    private DomainService domainService;

    /**
     * 添加一个域名
     *
     * @param domainRequest Domain
     * @return Result
     */
    @RequestMapping("/add")
    public Result addDomain(DomainRequest domainRequest){
        return domainService.addDomain(domainRequest);
    }
}
