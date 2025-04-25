package com.byxfd.game.checkin.service.impl;

import com.byxfd.context.general.response.ResponseData;
import com.byxfd.core.base.entity.query.PageQueryArgs;
import com.byxfd.core.base.entity.query.PageQueryResult;
import com.byxfd.core.base.service.BaseService;
import com.byxfd.core.jdbc.query.QueryCriteria;
import com.byxfd.game.checkin.entity.Activity;
import com.byxfd.game.checkin.entity.Notification;
import com.byxfd.game.checkin.entity.Participant;
import com.byxfd.game.checkin.service.ActivityService;
import com.byxfd.game.checkin.service.CheckinService;
import com.byxfd.game.checkin.service.NotificationService;
import com.byxfd.game.checkin.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl extends BaseService<Notification> implements NotificationService {

    @Autowired
    private ActivityService activityService;
    
    @Autowired
    private ParticipantService participantService;
    
    @Autowired
    private CheckinService checkinService;

    @Override
    public PageQueryResult findPage(PageQueryArgs pageQueryArgs) {
        return super.findPage(pageQueryArgs);
    }

    @Override
    public ResponseData createEntity(Notification resources) {
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
    public ResponseData getCustomerNotifications(Long customerId) {
        List<QueryCriteria> conditions = new ArrayList<>();
        QueryCriteria userIdCriteria = new QueryCriteria();
        userIdCriteria.setField("customerId");
        userIdCriteria.setOperator("eq");
        userIdCriteria.setValue(customerId);
        conditions.add(userIdCriteria);
        
        return ResponseData.success(super.findEntities(conditions));
    }

    @Override
    public ResponseData markAsRead(Long notificationId) {
        Map<String, Object> updateData = new HashMap<>();
        updateData.put("isRead", 1);
        
        Map<String, Object> condition = new HashMap<>();
        condition.put("id", notificationId);
        
        return super.updateByCondition(updateData, condition);
    }

    @Override
    public ResponseData markAllAsRead(Long customerId) {
        Map<String, Object> updateData = new HashMap<>();
        updateData.put("isRead", 1);
        
        Map<String, Object> condition = new HashMap<>();
        condition.put("customerId", customerId);
        condition.put("isRead", 0);
        
        return super.updateByCondition(updateData, condition);
    }

    @Override
    public ResponseData sendActivityReminder(Long activityId) {
        try {
            // 直接调用服务方法获取活动详情
            ResponseData activityResponse = activityService.getActivityDetail(activityId);
            if (activityResponse.hasError()) {
                return ResponseData.error("获取活动详情失败: " + activityResponse.getMessage());
            }
            
            Activity activity = (Activity) activityResponse.getData();
            if (activity == null) {
                return ResponseData.error("活动不存在");
            }
            
            // 直接调用服务方法获取活动参与者
            ResponseData participantsResponse = participantService.getActivityParticipants(activityId);
            if (participantsResponse.hasError()) {
                return ResponseData.error("获取参与者失败: " + participantsResponse.getMessage());
            }
            
            @SuppressWarnings("unchecked")
            List<Participant> participants = (List<Participant>) participantsResponse.getData();
            if (participants == null || participants.isEmpty()) {
                return ResponseData.error("活动没有参与者");
            }
            
            // 给每个参与者发送提醒通知
            int successCount = 0;
            for (Participant p : participants) {
                Notification notification = new Notification();
                notification.setCustomerId(p.getCustomerId());
                notification.setActivityId(activityId);
                notification.setType(1); // 1-活动提醒
                notification.setTitle("活动即将开始");
                notification.setContent("您参与的活动「" + activity.getTitle() + "」将于" 
                        + activity.getActivityTime() + "开始，请准时打卡。");
                notification.setIsRead(0);
                notification.setCreateTime(LocalDateTime.now());
                
                ResponseData result = createEntity(notification);
                if (!result.hasError()) {
                    successCount++;
                }
            }
            
            return ResponseData.success("成功发送" + successCount + "条活动提醒通知");
        } catch (Exception e) {
            logger.error("发送活动提醒失败", e);
            return ResponseData.error("发送活动提醒失败: " + e.getMessage());
        }
    }

    @Override
    public ResponseData sendCheckinReminder(Long activityId) {
        try {
            // 直接调用服务方法获取活动详情
            ResponseData activityResponse = activityService.getActivityDetail(activityId);
            if (activityResponse.hasError()) {
                return ResponseData.error("获取活动详情失败: " + activityResponse.getMessage());
            }
            
            Activity activity = (Activity) activityResponse.getData();
            if (activity == null) {
                return ResponseData.error("活动不存在");
            }
            
            // 直接调用服务方法获取活动参与者
            ResponseData participantsResponse = participantService.getActivityParticipants(activityId);
            if (participantsResponse.hasError()) {
                return ResponseData.error("获取参与者失败: " + participantsResponse.getMessage());
            }
            
            @SuppressWarnings("unchecked")
            List<Participant> participants = (List<Participant>) participantsResponse.getData();
            if (participants == null || participants.isEmpty()) {
                return ResponseData.error("活动没有参与者");
            }
            
            // 直接调用服务方法获取已签到的用户
            ResponseData checkinsResponse = checkinService.getActivityCheckins(activityId);
            if (checkinsResponse.hasError()) {
                return ResponseData.error("获取签到记录失败: " + checkinsResponse.getMessage());
            }
            
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> checkins = (List<Map<String, Object>>) checkinsResponse.getData();
            Set<Long> checkedInCustomerIds = checkins.stream()
                    .map(c -> Long.valueOf(String.valueOf(c.get("userId"))))
                    .collect(Collectors.toSet());
            
            // 给未签到的参与者发送提醒通知
            int reminderCount = 0;
            for (Participant p : participants) {
                // 只提醒未签到的用户
                if (!checkedInCustomerIds.contains(p.getCustomerId())) {
                    Notification notification = new Notification();
                    notification.setCustomerId(p.getCustomerId());
                    notification.setActivityId(activityId);
                    notification.setType(2); // 2-签到提醒
                    notification.setTitle("签到提醒");
                    notification.setContent("您参与的活动「" + activity.getTitle() + "」正在进行中，请及时完成签到，" 
                            + "否则押金将被扣除。");
                    notification.setIsRead(0);
                    notification.setCreateTime(LocalDateTime.now());
                    
                    ResponseData result = createEntity(notification);
                    if (!result.hasError()) {
                        reminderCount++;
                    }
                }
            }
            
            if (reminderCount > 0) {
                return ResponseData.success("已成功发送" + reminderCount + "条签到提醒");
            } else {
                return ResponseData.success("所有参与者均已签到，无需发送提醒");
            }
        } catch (Exception e) {
            logger.error("发送签到提醒失败", e);
            return ResponseData.error("发送签到提醒失败: " + e.getMessage());
        }
    }

    @Override
    public ResponseData sendDepositChangeNotification(Long customerId, Long activityId, String message) {
        try {
            // 直接调用服务方法获取活动详情
            ResponseData activityResponse = activityService.getActivityDetail(activityId);
            if (activityResponse.hasError()) {
                return ResponseData.error("获取活动详情失败: " + activityResponse.getMessage());
            }
            
            Activity activity = (Activity) activityResponse.getData();
            if (activity == null) {
                return ResponseData.error("活动不存在");
            }
            
            // 创建通知
            Notification notification = new Notification();
            notification.setCustomerId(customerId);
            notification.setActivityId(activityId);
            notification.setType(3); // 3-押金变动
            notification.setTitle("押金变动通知");
            notification.setContent("您参与的活动「" + activity.getTitle() + "」" + message);
            notification.setIsRead(0);
            notification.setCreateTime(LocalDateTime.now());
            
            return createEntity(notification);
        } catch (Exception e) {
            logger.error("发送押金变动通知失败", e);
            return ResponseData.error("发送押金变动通知失败: " + e.getMessage());
        }
    }

    @Override
    public ResponseData sendSystemNotification(String title, String content, Set<Long> customerIds) {
        try {
            int successCount = 0;
            for (Long customerId : customerIds) {
                Notification notification = new Notification();
                notification.setCustomerId(customerId);
                notification.setActivityId(null);
                notification.setType(5); // 5-系统公告
                notification.setTitle(title);
                notification.setContent(content);
                notification.setIsRead(0);
                notification.setCreateTime(LocalDateTime.now());
                
                ResponseData response = createEntity(notification);
                if (!response.hasError()) {
                    successCount++;
                }
            }
            
            return ResponseData.success("成功发送" + successCount + "条系统通知");
        } catch (Exception e) {
            return ResponseData.error("发送系统通知失败: " + e.getMessage());
        }
    }
} 