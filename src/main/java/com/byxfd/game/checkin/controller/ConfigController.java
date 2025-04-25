package com.byxfd.game.checkin.controller;

import com.byxfd.context.general.response.ResponseData;
import com.byxfd.core.base.controller.BaseController;
import com.byxfd.core.base.entity.query.PageQueryArgs;
import com.byxfd.game.checkin.entity.Config;
import com.byxfd.game.checkin.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/config")
public class ConfigController extends BaseController {
    @Autowired
    private ConfigService configService;

    @GetMapping
    public ResponseData query(@RequestBody PageQueryArgs pageQueryArgs) {
        return ResponseData.success(configService.findPage(pageQueryArgs));
    }

    @PostMapping
    public ResponseData create(@Validated @RequestBody Config resources) {
        return configService.createEntity(resources);
    }
    
    @PutMapping
    public ResponseData update(@RequestBody Map<String, Object> resources) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", resources.remove("id"));
        return configService.updateByCondition(resources, map);
    }
    
    @DeleteMapping
    public ResponseData delete(@RequestBody Set<Object> ids) {
        return configService.batchDelete(ids);
    }
    
    @GetMapping("/{key}")
    public ResponseData getConfigByKey(@PathVariable String key) {
        return configService.getConfigByKey(key);
    }
    
    @PutMapping("/{key}")
    public ResponseData updateConfig(@PathVariable String key, @RequestBody Map<String, String> valueMap) {
        String value = valueMap.get("value");
        return configService.updateConfig(key, value);
    }
    
    @GetMapping("/all")
    public ResponseData getAllConfigs() {
        return configService.getAllConfigs();
    }
} 