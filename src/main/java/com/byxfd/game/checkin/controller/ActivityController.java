package com.byxfd.game.checkin.controller;

import com.byxfd.context.general.response.ResponseData;
import com.byxfd.core.base.controller.BaseController;
import com.byxfd.core.base.entity.query.PageQueryArgs;
import com.byxfd.game.checkin.entity.Activity;
import com.byxfd.game.checkin.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/activity")
public class ActivityController extends BaseController {
    @Autowired
    private ActivityService activityService;

    @GetMapping
    public ResponseData query(@RequestBody PageQueryArgs pageQueryArgs) {
        return ResponseData.success(activityService.findPage(pageQueryArgs));
    }

    @PostMapping
    public ResponseData create(@Validated @RequestBody Activity resources) {
        return activityService.createEntity(resources);
    }
    
    @PutMapping
    public ResponseData update(@RequestBody Map<String, Object> resources) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", resources.remove("id"));
        return activityService.updateByCondition(resources, map);
    }
    
    @DeleteMapping
    public ResponseData delete(@RequestBody Set<Object> ids) {
        return activityService.batchDelete(ids);
    }
    
    @GetMapping("/{activityId}")
    public ResponseData getDetail(@PathVariable Long activityId) {
        return activityService.getActivityDetail(activityId);
    }
    
    @PutMapping("/status/{activityId}/{status}")
    public ResponseData updateStatus(@PathVariable Long activityId, @PathVariable Integer status) {
        return activityService.updateActivityStatus(activityId, status);
    }
    
    @GetMapping("/user/{customerId}/{status}")
    public ResponseData getUserActivities(@PathVariable Long customerId, @PathVariable Integer status) {
        return activityService.getUserActivities(customerId, status);
    }
} 