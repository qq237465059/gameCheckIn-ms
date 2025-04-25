package com.byxfd.game.checkin.service.impl;

import com.byxfd.context.general.response.ResponseData;
import com.byxfd.core.base.entity.query.PageQueryArgs;
import com.byxfd.core.base.entity.query.PageQueryResult;
import com.byxfd.core.base.service.BaseService;
import com.byxfd.core.jdbc.query.QueryCriteria;
import com.byxfd.game.checkin.entity.Activity;
import com.byxfd.game.checkin.service.ActivityService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ActivityServiceImpl extends BaseService<Activity> implements ActivityService {

    @Override
    public PageQueryResult findPage(PageQueryArgs pageQueryArgs) {
        return super.findPage(pageQueryArgs);
    }

    @Override
    public ResponseData createEntity(Activity resources) {
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
    public ResponseData getActivityDetail(Long activityId) {
        return ResponseData.success(super.getEntity(activityId));
    }

    @Override
    public ResponseData updateActivityStatus(Long activityId, Integer status) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("id", activityId);
        
        Map<String, Object> updateData = new HashMap<>();
        updateData.put("status", status);
        
        return super.updateByCondition(updateData, condition);
    }

    @Override
    public ResponseData getUserActivities(Long customerId, Integer status) {
        List<QueryCriteria> conditions = new ArrayList<>();
        
        QueryCriteria creatorCriteria = new QueryCriteria();
        creatorCriteria.setField("creatorId");
        creatorCriteria.setOperator("eq");
        creatorCriteria.setValue(customerId);
        conditions.add(creatorCriteria);
        
        if (status != null) {
            QueryCriteria statusCriteria = new QueryCriteria();
            statusCriteria.setField("status");
            statusCriteria.setOperator("eq");
            statusCriteria.setValue(status);
            conditions.add(statusCriteria);
        }
        
        return ResponseData.success(super.findEntities(conditions));
    }
} 