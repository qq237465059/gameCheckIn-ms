package com.byxfd.game.checkin.service;

import com.byxfd.context.general.response.ResponseData;
import com.byxfd.core.base.entity.query.PageQueryArgs;
import com.byxfd.core.base.entity.query.PageQueryResult;
import com.byxfd.game.checkin.entity.Notification;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public interface NotificationService {
    PageQueryResult findPage(PageQueryArgs pageQueryArgs);
    ResponseData createEntity(Notification resources);
    ResponseData updateByCondition(Map<String, Object> resources, Map<String, Object> condition);
    ResponseData batchDelete(Set<Object> ids);
    ResponseData getCustomerNotifications(Long customerId);
    ResponseData markAsRead(Long notificationId);
    ResponseData markAllAsRead(Long customerId);
    ResponseData sendActivityReminder(Long activityId);
    ResponseData sendCheckinReminder(Long activityId);
    ResponseData sendDepositChangeNotification(Long customerId, Long activityId, String message);
    ResponseData sendSystemNotification(String title, String content, Set<Long> customerIds);
} 