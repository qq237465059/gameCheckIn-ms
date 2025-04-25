package com.byxfd.game.checkin.service.impl;

import com.byxfd.context.general.response.ResponseData;
import com.byxfd.core.base.entity.query.PageQueryArgs;
import com.byxfd.core.base.entity.query.PageQueryResult;
import com.byxfd.core.base.service.BaseService;
import com.byxfd.game.checkin.entity.Sample;
import com.byxfd.game.checkin.service.SampleService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public class SampleServiceImpl extends BaseService<Sample> implements SampleService {

    @Override
    public PageQueryResult findPage(PageQueryArgs pageQueryArgs) {
        return super.findPage(pageQueryArgs);
    }

    @Override
    public ResponseData createEntity(Sample resources) {
        return super.createEntity(resources);
    }

    @Override
    public ResponseData updateByCondition(Map resources, Map condition) {
        return super.updateByCondition(resources, condition);
    }

    @Override
    public ResponseData batchDelete(Set<Object> ids) {
        return super.batchDelete(ids);
    }
}
