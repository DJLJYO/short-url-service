package com.fasturl.shorturlservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasturl.shorturlservice.mapper.DomainMapper;
import com.fasturl.shorturlservice.model.Domain;
import com.fasturl.shorturlservice.model.Result;
import com.fasturl.shorturlservice.model.request.DomainRequest;
import com.fasturl.shorturlservice.service.DomainService;
import org.springframework.stereotype.Service;

/**
 * DomainImpl
 *
 * @author quanyi
 * @since 2022-12-24 14:33:43
 */
@Service
public class DomainImpl extends ServiceImpl<DomainMapper, Domain> implements DomainService {
    @Override
    public Result addDomain(DomainRequest domainRequest) {
        LambdaQueryWrapper<Domain> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Domain::getDomain, domainRequest.getDomain());
        Domain domain = baseMapper.selectOne(lambdaQueryWrapper);
        if (domain == null) {
            domain = new Domain();
            domain.setDomain(domainRequest.getDomain());
            domain.setStatus(domainRequest.getStatus());
            if (baseMapper.insert(domain) == 1) {
                return Result.success(domainRequest.getDomain());
            }
        }
        return Result.fail("insert fail");
    }

    @Override
    public Domain queryById(int id) {
        return baseMapper.selectById(id);
    }
}
