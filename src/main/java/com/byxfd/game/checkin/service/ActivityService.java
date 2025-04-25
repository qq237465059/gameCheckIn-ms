package com.byxfd.game.checkin.service;

import com.byxfd.context.general.response.ResponseData;
import com.byxfd.core.base.entity.query.PageQueryArgs;
import com.byxfd.core.base.entity.query.PageQueryResult;
import com.byxfd.game.checkin.entity.Activity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public interface ActivityService {
    PageQueryResult findPage(PageQueryArgs pageQueryArgs);
    ResponseData createEntity(Activity resources);
    ResponseData updateByCondition(Map<String, Object> resources, Map<String, Object> condition);
    ResponseData batchDelete(Set<Object> ids);
    ResponseData getActivityDetail(Long activityId);
    ResponseData updateActivityStatus(Long activityId, Integer status);
    ResponseData getUserActivities(Long customerId, Integer status);
} 