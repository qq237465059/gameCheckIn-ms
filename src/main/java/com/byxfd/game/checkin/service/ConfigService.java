package com.byxfd.game.checkin.service;

import com.byxfd.context.general.response.ResponseData;
import com.byxfd.core.base.entity.query.PageQueryArgs;
import com.byxfd.core.base.entity.query.PageQueryResult;
import com.byxfd.game.checkin.entity.Config;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public interface ConfigService {
    PageQueryResult findPage(PageQueryArgs pageQueryArgs);
    ResponseData createEntity(Config resources);
    ResponseData updateByCondition(Map<String, Object> resources, Map<String, Object> condition);
    ResponseData batchDelete(Set<Object> ids);
    ResponseData getConfigByKey(String key);
    ResponseData updateConfig(String key, String value);
    ResponseData getAllConfigs();
} 