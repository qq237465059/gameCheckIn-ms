package com.byxfd.game.checkin.controller;

import com.byxfd.context.general.response.ResponseData;
import com.byxfd.core.base.controller.BaseController;
import com.byxfd.core.base.entity.query.PageQueryArgs;
import com.byxfd.game.checkin.entity.Notification;
import com.byxfd.game.checkin.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/notification")
public class NotificationController extends BaseController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public ResponseData query(@RequestBody PageQueryArgs pageQueryArgs) {
        return ResponseData.success(notificationService.findPage(pageQueryArgs));
    }

    @PostMapping
    public ResponseData create(@Validated @RequestBody Notification resources) {
        return notificationService.createEntity(resources);
    }
    
    @PutMapping
    public ResponseData update(@RequestBody Map<String, Object> resources) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", resources.remove("id"));
        return notificationService.updateByCondition(resources, map);
    }
    
    @DeleteMapping
    public ResponseData delete(@RequestBody Set<Object> ids) {
        return notificationService.batchDelete(ids);
    }
    
    @GetMapping("/user/{customerId}")
    public ResponseData getUserNotifications(@PathVariable Long customerId) {
        return notificationService.getCustomerNotifications(customerId);
    }
    
    @PutMapping("/read/{notificationId}")
    public ResponseData markAsRead(@PathVariable Long notificationId) {
        return notificationService.markAsRead(notificationId);
    }
    
    @PutMapping("/read-all/{customerId}")
    public ResponseData markAllAsRead(@PathVariable Long customerId) {
        return notificationService.markAllAsRead(customerId);
    }
    
    @PostMapping("/send/activity-reminder/{activityId}")
    public ResponseData sendActivityReminder(@PathVariable Long activityId) {
        return notificationService.sendActivityReminder(activityId);
    }
    
    @PostMapping("/send/checkin-reminder/{activityId}")
    public ResponseData sendCheckinReminder(@PathVariable Long activityId) {
        return notificationService.sendCheckinReminder(activityId);
    }
    
    @PostMapping("/send/deposit-change")
    public ResponseData sendDepositChangeNotification(@RequestBody Map<String, Object> notificationInfo) {
        Long customerId = Long.valueOf(notificationInfo.get("customerId").toString());
        Long activityId = Long.valueOf(notificationInfo.get("activityId").toString());
        String message = (String) notificationInfo.get("message");
        return notificationService.sendDepositChangeNotification(customerId, activityId, message);
    }
    
    @PostMapping("/send/system-notification")
    public ResponseData sendSystemNotification(@RequestBody Map<String, Object> notificationInfo) {
        String title = (String) notificationInfo.get("title");
        String content = (String) notificationInfo.get("content");
        @SuppressWarnings("unchecked")
        Set<Long> customerIds = (Set<Long>) notificationInfo.get("customerIds");
        return notificationService.sendSystemNotification(title, content, customerIds);
    }
} 