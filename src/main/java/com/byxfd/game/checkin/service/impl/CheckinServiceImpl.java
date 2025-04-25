package com.byxfd.game.checkin.service.impl;

import com.byxfd.context.general.response.ResponseData;
import com.byxfd.core.base.entity.query.PageQueryArgs;
import com.byxfd.core.base.entity.query.PageQueryResult;
import com.byxfd.core.base.service.BaseService;
import com.byxfd.core.jdbc.query.QueryCriteria;
import com.byxfd.game.checkin.entity.Checkin;
import com.byxfd.game.checkin.entity.Participant;
import com.byxfd.game.checkin.service.CheckinService;
import com.byxfd.game.checkin.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class CheckinServiceImpl extends BaseService<Checkin> implements CheckinService {

    @Autowired
    private ParticipantService participantService;

    @Override
    public PageQueryResult findPage(PageQueryArgs pageQueryArgs) {
        return super.findPage(pageQueryArgs);
    }

    @Override
    public ResponseData createEntity(Checkin resources) {
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
    public ResponseData doCheckin(Checkin checkin) {
        try {
            // 设置打卡时间
            checkin.setCheckinTime(LocalDateTime.now());
            
            // 验证位置
            Map<String, Object> locationInfo = new HashMap<>();
            locationInfo.put("customerId", checkin.getCustomerId());
            locationInfo.put("activityId", checkin.getActivityId());
            locationInfo.put("checkinLatitude", checkin.getCheckinLatitude());
            locationInfo.put("checkinLongitude", checkin.getCheckinLongitude());
            
            ResponseData locationResult = verifyCheckinLocation(locationInfo);
            if (locationResult.hasError()) {
                return locationResult;
            }
            
            // 创建打卡记录
            ResponseData createResult = super.createEntity(checkin);
            if (!createResult.hasError()) {
                // 查询用户参与记录
                ResponseData participantResponse = participantService.getUserParticipations(checkin.getCustomerId());
                if (!participantResponse.hasError() && participantResponse.getData() != null) {
                    List<Participant> participants = (List<Participant>) participantResponse.getData();
                    for (Participant p : participants) {
                        if (p.getActivityId().equals(checkin.getActivityId())) {
                            // 更新参与者状态为已打卡
                            participantService.updateParticipantStatus(p.getId(), 3); // 状态3：已打卡
                            break;
                        }
                    }
                }
            }
            
            return createResult;
        } catch (Exception e) {
            return ResponseData.error("打卡失败: " + e.getMessage());
        }
    }

    @Override
    public ResponseData getActivityCheckins(Long activityId) {
        List<QueryCriteria> conditions = new ArrayList<>();
        
        QueryCriteria activityIdCriteria = new QueryCriteria();
        activityIdCriteria.setField("activityId");
        activityIdCriteria.setOperator("eq");
        activityIdCriteria.setValue(activityId);
        conditions.add(activityIdCriteria);
        
        return ResponseData.success(super.findEntities(conditions));
    }

    @Override
    public ResponseData getUserCheckins(Long customerId) {
        List<QueryCriteria> conditions = new ArrayList<>();
        
        QueryCriteria customerIdCriteria = new QueryCriteria();
        customerIdCriteria.setField("customerId");
        customerIdCriteria.setOperator("eq");
        customerIdCriteria.setValue(customerId);
        conditions.add(customerIdCriteria);
        
        return ResponseData.success(super.findEntities(conditions));
    }

    @Override
    public ResponseData verifyCheckinLocation(Map<String, Object> locationInfo) {
        try {
            Long customerId = (Long) locationInfo.get("customerId");
            Long activityId = (Long) locationInfo.get("activityId");
            BigDecimal checkinLatitude = (BigDecimal) locationInfo.get("checkinLatitude");
            BigDecimal checkinLongitude = (BigDecimal) locationInfo.get("checkinLongitude");
            
            // 获取参与者记录
            ResponseData participantsResponse = participantService.getUserParticipations(customerId);
            if (participantsResponse.hasError()) {
                return ResponseData.error("获取用户参与记录失败");
            }
            
            List<Participant> participants = (List<Participant>) participantsResponse.getData();
            Participant participant = null;
            
            // 查找对应活动的参与记录
            for (Participant p : participants) {
                if (p.getActivityId().equals(activityId)) {
                    participant = p;
                    break;
                }
            }
            
            if (participant == null) {
                return ResponseData.error("未找到该活动的参与记录");
            }
            
            // 计算距离，判断是否在范围内
            // 这里应该有计算两个经纬度之间距离的逻辑，简化实现
            BigDecimal latDiff = participant.getLatitude().subtract(checkinLatitude).abs();
            BigDecimal lngDiff = participant.getLongitude().subtract(checkinLongitude).abs();
            
            // 简化的距离计算
            BigDecimal distance = latDiff.add(lngDiff).multiply(new BigDecimal(100000)); 
            
            if (distance.compareTo(new BigDecimal(participant.getRange())) <= 0) {
                return ResponseData.success(true);
            } else {
                return ResponseData.error("您不在打卡范围内");
            }
        } catch (Exception e) {
            return ResponseData.error("验证打卡位置失败: " + e.getMessage());
        }
    }
} 