package com.byxfd.game.checkin.controller;

import com.byxfd.context.general.response.ResponseData;
import com.byxfd.core.base.controller.BaseController;
import com.byxfd.core.base.entity.query.PageQueryArgs;
import com.byxfd.game.checkin.entity.DepositRecord;
import com.byxfd.game.checkin.service.DepositRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/deposit")
public class DepositRecordController extends BaseController {
    @Autowired
    private DepositRecordService depositRecordService;

    @GetMapping
    public ResponseData query(@RequestBody PageQueryArgs pageQueryArgs) {
        return ResponseData.success(depositRecordService.findPage(pageQueryArgs));
    }

    @PostMapping
    public ResponseData create(@Validated @RequestBody DepositRecord resources) {
        return depositRecordService.createEntity(resources);
    }
    
    @PutMapping
    public ResponseData update(@RequestBody Map<String, Object> resources) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", resources.remove("id"));
        return depositRecordService.updateByCondition(resources, map);
    }
    
    @DeleteMapping
    public ResponseData delete(@RequestBody Set<Object> ids) {
        return depositRecordService.batchDelete(ids);
    }
    
    @GetMapping("/user/{customerId}")
    public ResponseData getUserDepositRecords(@PathVariable Long customerId) {
        return depositRecordService.getCustomerDepositRecords(customerId);
    }
    
    @PostMapping("/deposit")
    public ResponseData deposit(@RequestBody Map<String, Object> depositInfo) {
        Long customerId = Long.valueOf(depositInfo.get("customerId").toString());
        BigDecimal amount = new BigDecimal(depositInfo.get("amount").toString());
        String transactionId = (String) depositInfo.get("transactionId");
        return depositRecordService.deposit(customerId, amount, transactionId);
    }
    
    @PostMapping("/freeze")
    public ResponseData freezeDeposit(@RequestBody Map<String, Object> freezeInfo) {
        Long customerId = Long.valueOf(freezeInfo.get("customerId").toString());
        Long activityId = Long.valueOf(freezeInfo.get("activityId").toString());
        BigDecimal amount = new BigDecimal(freezeInfo.get("amount").toString());
        return depositRecordService.freezeDeposit(customerId, activityId, amount);
    }
    
    @PostMapping("/unfreeze")
    public ResponseData unfreezeDeposit(@RequestBody Map<String, Object> unfreezeInfo) {
        Long customerId = Long.valueOf(unfreezeInfo.get("customerId").toString());
        Long activityId = Long.valueOf(unfreezeInfo.get("activityId").toString());
        BigDecimal amount = new BigDecimal(unfreezeInfo.get("amount").toString());
        return depositRecordService.unfreezeDeposit(customerId, activityId, amount);
    }
    
    @PostMapping("/deduct")
    public ResponseData deductDeposit(@RequestBody Map<String, Object> deductInfo) {
        Long customerId = Long.valueOf(deductInfo.get("customerId").toString());
        Long activityId = Long.valueOf(deductInfo.get("activityId").toString());
        BigDecimal amount = new BigDecimal(deductInfo.get("amount").toString());
        return depositRecordService.deductDeposit(customerId, activityId, amount);
    }
    
    @PostMapping("/distribute/{activityId}")
    public ResponseData distributeDeposit(@PathVariable Long activityId) {
        return depositRecordService.distributeDeposit(activityId);
    }
} 