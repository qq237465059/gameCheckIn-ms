package com.byxfd.game.checkin.service;

import com.byxfd.context.general.response.ResponseData;
import com.byxfd.core.base.entity.query.PageQueryArgs;
import com.byxfd.core.base.entity.query.PageQueryResult;
import com.byxfd.game.checkin.entity.Customer;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public interface CustomerService {
    PageQueryResult findPage(PageQueryArgs pageQueryArgs);
    ResponseData createEntity(Customer resources);
    ResponseData updateByCondition(Map resources, Map condition);
    ResponseData batchDelete(Set<Object> ids);
    ResponseData getCustomerByOpenId(String openId);
    ResponseData getCustomerById(Long customerId);
    ResponseData updateCustomerBalance(Long customerId, Map<String, Object> balanceInfo);
} 