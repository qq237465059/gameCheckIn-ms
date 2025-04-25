package com.byxfd.game.checkin.service.impl;

import com.byxfd.context.general.response.ResponseData;
import com.byxfd.core.base.entity.query.PageQueryArgs;
import com.byxfd.core.base.entity.query.PageQueryResult;
import com.byxfd.core.base.service.BaseService;
import com.byxfd.core.jdbc.query.QueryCriteria;
import com.byxfd.game.checkin.entity.Customer;
import com.byxfd.game.checkin.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class CustomerServiceImpl extends BaseService<Customer> implements CustomerService {

    @Override
    public PageQueryResult findPage(PageQueryArgs pageQueryArgs) {
        return super.findPage(pageQueryArgs);
    }

    @Override
    public ResponseData createEntity(Customer resources) {
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
    public ResponseData getCustomerByOpenId(String openId) {
        List<QueryCriteria> conditions = new ArrayList<>();
        
        QueryCriteria openIdCriteria = new QueryCriteria();
        openIdCriteria.setField("openId");
        openIdCriteria.setOperator("eq");
        openIdCriteria.setValue(openId);
        conditions.add(openIdCriteria);
        
        List<Customer> customers = super.findEntities(conditions);
        return ResponseData.success(customers != null && !customers.isEmpty() ? customers.get(0) : null);
    }

    @Override
    public ResponseData getCustomerById(Long customerId) {
        return ResponseData.success(super.getEntity(customerId));
    }

    @Override
    public ResponseData updateCustomerBalance(Long customerId, Map<String, Object> balanceInfo) {
        Map<String, Object> condition = new HashMap<>();
        condition.put("id", customerId);
        return super.updateByCondition(balanceInfo, condition);
    }
} 