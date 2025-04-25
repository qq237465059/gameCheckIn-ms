package com.byxfd.game.checkin.controller;

import com.byxfd.context.general.response.ResponseData;
import com.byxfd.core.base.controller.BaseController;
import com.byxfd.core.base.entity.query.PageQueryArgs;
import com.byxfd.game.checkin.entity.Customer;
import com.byxfd.game.checkin.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/customer")
public class CustomerController extends BaseController {
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseData query(@RequestBody PageQueryArgs pageQueryArgs) {
        return ResponseData.success(customerService.findPage(pageQueryArgs));
    }

    @PostMapping
    public ResponseData create(@Validated @RequestBody Customer resources) {
        return customerService.createEntity(resources);
    }
    
    @PutMapping
    public ResponseData update(@RequestBody Map<String, Object> resources) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", resources.remove("id"));
        return customerService.updateByCondition(resources, map);
    }
    
    @DeleteMapping
    public ResponseData delete(@RequestBody Set<Object> ids) {
        return customerService.batchDelete(ids);
    }
    
    @GetMapping("/{openId}")
    public ResponseData getByOpenId(@PathVariable String openId) {
        return customerService.getCustomerByOpenId(openId);
    }
    
    @PutMapping("/balance/{customerId}")
    public ResponseData updateBalance(@PathVariable Long customerId, @RequestBody Map<String, Object> balanceInfo) {
        return customerService.updateCustomerBalance(customerId, balanceInfo);
    }
} 