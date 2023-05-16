package com.fasturl.shorturlservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fasturl.shorturlservice.model.Domain;
import com.fasturl.shorturlservice.model.Result;
import com.fasturl.shorturlservice.model.request.DomainRequest;

/**
 * DomainService
 *
 * @author quanyi
 * @since 2022-12-24 14:33:48
 */
public interface DomainService extends IService<Domain> {
    /**
     * 加入一条域名
     *
     * @param domainRequest 域名
     * @return Result
     */
    Result addDomain(DomainRequest domainRequest);

    /**
     * 根据ID查一条
     *
     * @param id 域名id
     * @return Result
     */
    Domain queryById(int id);

    /**
     * 根据域名domain查一条
     *
     * @param domain 域名
     * @return Result
     */
    Domain queryByDomain(String domain);
}
