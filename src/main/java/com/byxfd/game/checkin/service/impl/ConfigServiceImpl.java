package com.byxfd.game.checkin.service.impl;

import com.byxfd.context.general.response.ResponseData;
import com.byxfd.core.base.entity.query.PageQueryArgs;
import com.byxfd.core.base.entity.query.PageQueryResult;
import com.byxfd.core.base.service.BaseService;
import com.byxfd.core.jdbc.query.QueryCriteria;
import com.byxfd.game.checkin.entity.Config;
import com.byxfd.game.checkin.service.ConfigService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ConfigServiceImpl extends BaseService<Config> implements ConfigService {

    @Override
    public PageQueryResult findPage(PageQueryArgs pageQueryArgs) {
        return super.findPage(pageQueryArgs);
    }

    @Override
    public ResponseData createEntity(Config resources) {
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

    @Override
    public ResponseData getConfigByKey(String key) {
        List<QueryCriteria> conditions = new ArrayList<>();
        QueryCriteria queryCriteria = new QueryCriteria();
        queryCriteria.setField("configKey");
        queryCriteria.setOperator("eq");
        queryCriteria.setValue(key);
        conditions.add(queryCriteria);
        
        List<Config> configs = super.findEntities(conditions);
        if (configs != null && !configs.isEmpty()) {
            return ResponseData.success(configs.get(0));
        }
        return ResponseData.success(null);
    }

    @Override
    public ResponseData updateConfig(String key, String value) {
        List<QueryCriteria> conditions = new ArrayList<>();
        QueryCriteria queryCriteria = new QueryCriteria();
        queryCriteria.setField("configKey");
        queryCriteria.setOperator("eq");
        queryCriteria.setValue(key);
        conditions.add(queryCriteria);
        
        List<Config> configs = super.findEntities(conditions);
        
        if (configs == null || configs.isEmpty()) {
            // 如果配置不存在，创建新配置
            Config newConfig = new Config();
            newConfig.setConfigKey(key);
            newConfig.setConfigValue(value);
            newConfig.setDescription("系统配置");
            newConfig.setCreateTime(LocalDateTime.now());
            newConfig.setUpdateTime(LocalDateTime.now());
            
            return super.createEntity(newConfig);
        } else {
            // 如果配置存在，更新配置值
            Config config = configs.get(0);
            Map<String, Object> updateData = new HashMap<>();
            updateData.put("configValue", value);
            updateData.put("updateTime", LocalDateTime.now());
            
            Map<String, Object> condition = new HashMap<>();
            condition.put("id", config.getId());
            
            return super.updateByCondition(updateData, condition);
        }
    }

    @Override
    public ResponseData getAllConfigs() {
        return ResponseData.success(super.findEntities(new ArrayList<>()));
    }
} 