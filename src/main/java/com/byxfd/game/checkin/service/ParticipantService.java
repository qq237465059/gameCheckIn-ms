package com.byxfd.game.checkin.service;

import com.byxfd.context.general.response.ResponseData;
import com.byxfd.core.base.entity.query.PageQueryArgs;
import com.byxfd.core.base.entity.query.PageQueryResult;
import com.byxfd.game.checkin.entity.Participant;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public interface ParticipantService {
    PageQueryResult findPage(PageQueryArgs pageQueryArgs);
    ResponseData createEntity(Participant resources);
    ResponseData updateByCondition(Map<String, Object> resources, Map<String, Object> condition);
    ResponseData batchDelete(Set<Object> ids);
    ResponseData getActivityParticipants(Long activityId);
    ResponseData getUserParticipations(Long customerId);
    ResponseData joinActivity(Participant participant);
    ResponseData updateParticipantStatus(Long id, Integer status);
} 