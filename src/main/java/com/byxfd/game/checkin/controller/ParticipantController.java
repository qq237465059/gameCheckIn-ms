package com.byxfd.game.checkin.controller;

import com.byxfd.context.general.response.ResponseData;
import com.byxfd.core.base.controller.BaseController;
import com.byxfd.core.base.entity.query.PageQueryArgs;
import com.byxfd.game.checkin.entity.Participant;
import com.byxfd.game.checkin.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/participant")
public class ParticipantController extends BaseController {
    @Autowired
    private ParticipantService participantService;

    @GetMapping
    public ResponseData query(@RequestBody PageQueryArgs pageQueryArgs) {
        return ResponseData.success(participantService.findPage(pageQueryArgs));
    }

    @PostMapping
    public ResponseData create(@Validated @RequestBody Participant resources) {
        return participantService.createEntity(resources);
    }
    
    @PutMapping
    public ResponseData update(@RequestBody Map<String, Object> resources) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", resources.remove("id"));
        return participantService.updateByCondition(resources, map);
    }
    
    @DeleteMapping
    public ResponseData delete(@RequestBody Set<Object> ids) {
        return participantService.batchDelete(ids);
    }
    
    @GetMapping("/activity/{activityId}")
    public ResponseData getActivityParticipants(@PathVariable Long activityId) {
        return participantService.getActivityParticipants(activityId);
    }
    
    @GetMapping("/user/{customerId}")
    public ResponseData getUserParticipations(@PathVariable Long customerId) {
        return participantService.getUserParticipations(customerId);
    }
    
    @PostMapping("/join")
    public ResponseData joinActivity(@RequestBody Participant participant) {
        return participantService.joinActivity(participant);
    }
    
    @PutMapping("/status/{id}/{status}")
    public ResponseData updateStatus(@PathVariable Long id, @PathVariable Integer status) {
        return participantService.updateParticipantStatus(id, status);
    }
} 