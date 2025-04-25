package com.byxfd.game.checkin.controller;

import com.byxfd.context.general.response.ResponseData;
import com.byxfd.core.base.controller.BaseController;
import com.byxfd.core.base.entity.query.PageQueryArgs;
import com.byxfd.game.checkin.entity.Checkin;
import com.byxfd.game.checkin.service.CheckinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/checkin")
public class CheckinController extends BaseController {
    @Autowired
    private CheckinService checkinService;

    @GetMapping
    public ResponseData query(@RequestBody PageQueryArgs pageQueryArgs) {
        return ResponseData.success(checkinService.findPage(pageQueryArgs));
    }

    @PostMapping
    public ResponseData create(@Validated @RequestBody Checkin resources) {
        return checkinService.createEntity(resources);
    }
    
    @PutMapping
    public ResponseData update(@RequestBody Map<String, Object> resources) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", resources.remove("id"));
        return checkinService.updateByCondition(resources, map);
    }
    
    @DeleteMapping
    public ResponseData delete(@RequestBody Set<Object> ids) {
        return checkinService.batchDelete(ids);
    }
    
    @PostMapping("/do-checkin")
    public ResponseData doCheckin(@RequestBody Checkin checkin) {
        return checkinService.doCheckin(checkin);
    }
    
    @GetMapping("/activity/{activityId}")
    public ResponseData getActivityCheckins(@PathVariable Long activityId) {
        return checkinService.getActivityCheckins(activityId);
    }
    
    @GetMapping("/user/{customerId}")
    public ResponseData getUserCheckins(@PathVariable Long customerId) {
        return checkinService.getUserCheckins(customerId);
    }
    
    @PostMapping("/verify-location")
    public ResponseData verifyCheckinLocation(@RequestBody Map<String, Object> locationInfo) {
        return checkinService.verifyCheckinLocation(locationInfo);
    }
} 