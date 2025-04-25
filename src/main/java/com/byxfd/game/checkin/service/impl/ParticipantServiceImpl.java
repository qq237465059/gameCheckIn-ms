package com.byxfd.game.checkin.service.impl;

import com.byxfd.context.general.response.ResponseData;
import com.byxfd.core.base.entity.query.PageQueryArgs;
import com.byxfd.core.base.entity.query.PageQueryResult;
import com.byxfd.core.base.service.BaseService;
import com.byxfd.core.jdbc.query.QueryCriteria;
import com.byxfd.game.checkin.entity.Participant;
import com.byxfd.game.checkin.service.ParticipantService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ParticipantServiceImpl extends BaseService<Participant> implements ParticipantService {

    @Override
    public PageQueryResult findPage(PageQueryArgs pageQueryArgs) {
        return super.findPage(pageQueryArgs);
    }

    @Override
    public ResponseData createEntity(Participant resources) {
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
    public ResponseData getActivityParticipants(Long activityId) {
        List<QueryCriteria> conditions = new ArrayList<>();
        
        QueryCriteria activityIdCriteria = new QueryCriteria();
        activityIdCriteria.setField("activityId");
        activityIdCriteria.setOperator("eq");
        activityIdCriteria.setValue(activityId);
        conditions.add(activityIdCriteria);
        
        return ResponseData.success(super.findEntities(conditions));
    }

    @Override
    public ResponseData getUserParticipations(Long customerId) {
        List<QueryCriteria> conditions = new ArrayList<>();
        
        QueryCriteria userIdCriteria = new QueryCriteria();
        userIdCriteria.setField("customerId");
        userIdCriteria.setOperator("eq");
        userIdCriteria.setValue(customerId);
        conditions.add(userIdCriteria);
        
        return ResponseData.success(super.findEntities(conditions));
    }

    @Override
    public ResponseData joinActivity(Participant participant) {
        // 先检查是否已经参与
        List<QueryCriteria> conditions = new ArrayList<>();
        
        QueryCriteria userIdCriteria = new QueryCriteria();
        userIdCriteria.setField("customerId");
        userIdCriteria.setOperator("eq");
        userIdCriteria.setValue(participant.getCustomerId());
        conditions.add(userIdCriteria);
        
        QueryCriteria activityIdCriteria = new QueryCriteria();
        activityIdCriteria.setField("activityId");
        activityIdCriteria.setOperator("eq");
        activityIdCriteria.setValue(participant.getActivityId());
        conditions.add(activityIdCriteria);
        
        List<Participant> existingParticipants = super.findEntities(conditions);
        if (existingParticipants != null && !existingParticipants.isEmpty()) {
            return ResponseData.error("您已经参与了该活动");
        }
        
        // 如果未参与，创建新参与记录
        return super.createEntity(participant);
    }

    @Override
    public ResponseData updateParticipantStatus(Long id, Integer status) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("id", id);
        
        Map<String, Object> updateData = new HashMap<>();
        updateData.put("status", status);
        
        return super.updateByCondition(updateData, condition);
    }
} 