package com.byxfd.game.checkin.service;

import com.byxfd.context.general.response.ResponseData;
import com.byxfd.core.base.entity.query.PageQueryArgs;
import com.byxfd.core.base.entity.query.PageQueryResult;
import com.byxfd.game.checkin.entity.DepositRecord;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

@Service
public interface DepositRecordService {
    PageQueryResult findPage(PageQueryArgs pageQueryArgs);
    ResponseData createEntity(DepositRecord resources);
    ResponseData updateByCondition(Map<String, Object> resources, Map<String, Object> condition);
    ResponseData batchDelete(Set<Object> ids);
    ResponseData getCustomerDepositRecords(Long customerId);
    ResponseData deposit(Long customerId, BigDecimal amount, String transactionId);
    ResponseData freezeDeposit(Long customerId, Long activityId, BigDecimal amount);
    ResponseData unfreezeDeposit(Long customerId, Long activityId, BigDecimal amount);
    ResponseData deductDeposit(Long customerId, Long activityId, BigDecimal amount);
    ResponseData distributeDeposit(Long activityId);
} 