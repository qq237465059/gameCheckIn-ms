package com.byxfd.game.checkin.service.impl;

import com.byxfd.context.general.response.ResponseData;
import com.byxfd.core.base.entity.query.PageQueryArgs;
import com.byxfd.core.base.entity.query.PageQueryResult;
import com.byxfd.core.base.service.BaseService;
import com.byxfd.core.jdbc.query.QueryCriteria;
import com.byxfd.game.checkin.entity.Activity;
import com.byxfd.game.checkin.entity.Customer;
import com.byxfd.game.checkin.entity.DepositRecord;
import com.byxfd.game.checkin.entity.Participant;
import com.byxfd.game.checkin.service.ActivityService;
import com.byxfd.game.checkin.service.CustomerService;
import com.byxfd.game.checkin.service.DepositRecordService;
import com.byxfd.game.checkin.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DepositRecordServiceImpl extends BaseService<DepositRecord> implements DepositRecordService {

    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private ActivityService activityService;
    
    @Autowired
    private ParticipantService participantService;

    @Override
    public PageQueryResult findPage(PageQueryArgs pageQueryArgs) {
        return super.findPage(pageQueryArgs);
    }

    @Override
    public ResponseData createEntity(DepositRecord resources) {
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
    public ResponseData getCustomerDepositRecords(Long customerId) {
        List<QueryCriteria> conditions = new ArrayList<>();
        
        QueryCriteria userIdCriteria = new QueryCriteria();
        userIdCriteria.setField("customerId");
        userIdCriteria.setOperator("eq");
        userIdCriteria.setValue(customerId);
        conditions.add(userIdCriteria);
        
        return ResponseData.success(super.findEntities(conditions));
    }

    @Override
    @Transactional
    public ResponseData deposit(Long customerId, BigDecimal amount, String transactionId) {
        try {
            // 获取客户信息
            ResponseData customerResponse = customerService.getCustomerById(customerId);
            if (customerResponse.hasError() || customerResponse.getData() == null) {
                return ResponseData.error("客户不存在");
            }
            
            Customer customer = (Customer) customerResponse.getData();
            
            // 更新客户余额
            BigDecimal newBalance = customer.getBalance().add(amount);
            Map<String, Object> balanceInfo = new HashMap<>();
            balanceInfo.put("balance", newBalance);
            customerService.updateCustomerBalance(customerId, balanceInfo);
            
            // 创建押金记录
            DepositRecord record = new DepositRecord();
            record.setCustomerId(customerId);
            record.setActivityId(null); // 充值不关联活动
            record.setType(1); // 1-充值
            record.setAmount(amount);
            record.setBalance(newBalance);
            record.setDescription("客户充值");
            record.setTransactionId(transactionId);
            record.setCreateTime(LocalDateTime.now());
            
            return super.createEntity(record);
        } catch (Exception e) {
            return ResponseData.error("充值失败: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public ResponseData freezeDeposit(Long customerId, Long activityId, BigDecimal amount) {
        try {
            // 获取客户信息
            ResponseData customerResponse = customerService.getCustomerById(customerId);
            if (customerResponse.hasError() || customerResponse.getData() == null) {
                return ResponseData.error("客户不存在");
            }
            
            Customer customer = (Customer) customerResponse.getData();
            
            if (customer.getBalance().compareTo(amount) < 0) {
                return ResponseData.error("余额不足");
            }
            
            // 更新客户余额和冻结金额
            BigDecimal newBalance = customer.getBalance().subtract(amount);
            BigDecimal newFrozenBalance = customer.getFrozenBalance().add(amount);
            
            Map<String, Object> balanceInfo = new HashMap<>();
            balanceInfo.put("balance", newBalance);
            balanceInfo.put("frozenBalance", newFrozenBalance);
            customerService.updateCustomerBalance(customerId, balanceInfo);
            
            // 创建押金记录
            DepositRecord record = new DepositRecord();
            record.setCustomerId(customerId);
            record.setActivityId(activityId);
            record.setType(2); // 2-冻结
            record.setAmount(amount);
            record.setBalance(newBalance);
            record.setDescription("活动押金冻结");
            record.setCreateTime(LocalDateTime.now());
            
            return super.createEntity(record);
        } catch (Exception e) {
            return ResponseData.error("冻结押金失败: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public ResponseData unfreezeDeposit(Long customerId, Long activityId, BigDecimal amount) {
        try {
            // 获取客户信息
            ResponseData customerResponse = customerService.getCustomerById(customerId);
            if (customerResponse.hasError() || customerResponse.getData() == null) {
                return ResponseData.error("客户不存在");
            }
            
            Customer customer = (Customer) customerResponse.getData();
            
            if (customer.getFrozenBalance().compareTo(amount) < 0) {
                return ResponseData.error("冻结余额不足");
            }
            
            // 更新客户余额和冻结金额
            BigDecimal newBalance = customer.getBalance().add(amount);
            BigDecimal newFrozenBalance = customer.getFrozenBalance().subtract(amount);
            
            Map<String, Object> balanceInfo = new HashMap<>();
            balanceInfo.put("balance", newBalance);
            balanceInfo.put("frozenBalance", newFrozenBalance);
            customerService.updateCustomerBalance(customerId, balanceInfo);
            
            // 创建押金记录
            DepositRecord record = new DepositRecord();
            record.setCustomerId(customerId);
            record.setActivityId(activityId);
            record.setType(3); // 3-解冻
            record.setAmount(amount);
            record.setBalance(newBalance);
            record.setDescription("活动押金解冻");
            record.setCreateTime(LocalDateTime.now());
            
            return super.createEntity(record);
        } catch (Exception e) {
            return ResponseData.error("解冻押金失败: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public ResponseData deductDeposit(Long customerId, Long activityId, BigDecimal amount) {
        try {
            // 获取客户信息
            ResponseData customerResponse = customerService.getCustomerById(customerId);
            if (customerResponse.hasError() || customerResponse.getData() == null) {
                return ResponseData.error("客户不存在");
            }
            
            Customer customer = (Customer) customerResponse.getData();
            
            if (customer.getFrozenBalance().compareTo(amount) < 0) {
                return ResponseData.error("冻结余额不足");
            }
            
            // 更新客户冻结金额
            BigDecimal newFrozenBalance = customer.getFrozenBalance().subtract(amount);
            
            Map<String, Object> balanceInfo = new HashMap<>();
            balanceInfo.put("frozenBalance", newFrozenBalance);
            customerService.updateCustomerBalance(customerId, balanceInfo);
            
            // 创建押金记录
            DepositRecord record = new DepositRecord();
            record.setCustomerId(customerId);
            record.setActivityId(activityId);
            record.setType(4); // 4-扣除
            record.setAmount(amount);
            record.setBalance(customer.getBalance());
            record.setDescription("未按时打卡，押金扣除");
            record.setCreateTime(LocalDateTime.now());
            
            return super.createEntity(record);
        } catch (Exception e) {
            return ResponseData.error("扣除押金失败: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public ResponseData distributeDeposit(Long activityId) {
        try {
            // 获取活动信息
            ResponseData activityResponse = activityService.getActivityDetail(activityId);
            if (activityResponse.hasError() || activityResponse.getData() == null) {
                return ResponseData.error("活动不存在");
            }
            
            Activity activity = (Activity) activityResponse.getData();
            
            // 查找活动参与者
            ResponseData participantsResponse = participantService.getActivityParticipants(activityId);
            if (participantsResponse.hasError()) {
                return ResponseData.error("获取活动参与者失败");
            }
            
            List<Participant> allParticipants = (List<Participant>) participantsResponse.getData();
            
            // 筛选已打卡和未打卡的参与者
            List<Participant> checkedInParticipants = allParticipants.stream()
                .filter(p -> p.getStatus() == 3) // 状态3：已打卡
                .collect(Collectors.toList());
            
            List<Participant> notCheckedInParticipants = allParticipants.stream()
                .filter(p -> p.getStatus() == 4) // 状态4：未打卡
                .collect(Collectors.toList());
            
            // 计算需要分配的总金额
            BigDecimal totalAmount = BigDecimal.ZERO;
            for (Participant p : notCheckedInParticipants) {
                // 扣除每个未打卡参与者的押金
                deductDeposit(p.getCustomerId(), activityId, p.getFrozenAmount());
                totalAmount = totalAmount.add(p.getFrozenAmount());
            }
            
            // 如果没有已打卡的参与者，无法分配
            if (checkedInParticipants.isEmpty()) {
                return ResponseData.error("没有已打卡的参与者，无法分配押金");
            }
            
            // 计算每个已打卡参与者可以分得的金额
            BigDecimal amountPerPerson = totalAmount.divide(new BigDecimal(checkedInParticipants.size()), 2, BigDecimal.ROUND_DOWN);
            
            // 给每个已打卡的参与者分配押金
            for (Participant p : checkedInParticipants) {
                // 获取客户信息
                ResponseData customerResponse = customerService.getCustomerById(p.getCustomerId());
                if (customerResponse.hasError() || customerResponse.getData() == null) {
                    continue;
                }
                
                Customer customer = (Customer) customerResponse.getData();
                
                // 更新客户余额
                BigDecimal newBalance = customer.getBalance().add(amountPerPerson);
                Map<String, Object> balanceInfo = new HashMap<>();
                balanceInfo.put("balance", newBalance);
                customerService.updateCustomerBalance(p.getCustomerId(), balanceInfo);
                
                // 创建押金记录
                DepositRecord record = new DepositRecord();
                record.setCustomerId(p.getCustomerId());
                record.setActivityId(activityId);
                record.setType(5); // 5-获得
                record.setAmount(amountPerPerson);
                record.setBalance(newBalance);
                record.setDescription("活动已完成，获得分配的押金");
                record.setCreateTime(LocalDateTime.now());
                
                super.createEntity(record);
                
                // 解冻押金
                unfreezeDeposit(p.getCustomerId(), activityId, p.getFrozenAmount());
            }
            
            return ResponseData.success("押金分配成功");
        } catch (Exception e) {
            return ResponseData.error("分配押金失败: " + e.getMessage());
        }
    }
} 