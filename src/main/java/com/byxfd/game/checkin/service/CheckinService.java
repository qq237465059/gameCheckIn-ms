package com.byxfd.game.checkin.service;

import com.byxfd.context.general.response.ResponseData;
import com.byxfd.core.base.entity.query.PageQueryArgs;
import com.byxfd.core.base.entity.query.PageQueryResult;
import com.byxfd.game.checkin.entity.Checkin;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public interface CheckinService {
    PageQueryResult findPage(PageQueryArgs pageQueryArgs);
    ResponseData createEntity(Checkin resources);
    ResponseData updateByCondition(Map<String, Object> resources, Map<String, Object> condition);
    ResponseData batchDelete(Set<Object> ids);
    ResponseData doCheckin(Checkin checkin);
    ResponseData getActivityCheckins(Long activityId);
    ResponseData getUserCheckins(Long customerId);
    ResponseData verifyCheckinLocation(Map<String, Object> locationInfo);
} 